package fr.imt.boomeuuuuh.lobbies;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.network.packets.server.EndGamePacket;
import fr.imt.boomeuuuuh.network.packets.server.LobbyInfoPacket;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.LobbyConnection;
import fr.imt.boomeuuuuh.network.packets.Packet;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

//Class for a lobby
//  -> Repertories players connected to it (and the subsequent game)
//  -> Manages UDP communications with the players
//  -> Manages information passed between players - eg. player skin that has to be relayed
//  -> Manages Chat

public class Lobby {

    public boolean running = true;

    private final LobbyConnection lobbyConnection;
    private final int udpPort;
    private final int lobbyID;

    private final Collection<Player> players;
    private Player owner;
    private String name;
    private boolean open = true;

    private GameManager gameManager;
    private LobbyState state = LobbyState.WAITING;

    public Lobby(int lobbyID, String name, Player owner) throws SocketException {
        this.lobbyConnection = new LobbyConnection();
        this.udpPort = lobbyConnection.getPort();
        this.lobbyID = lobbyID;

        this.players = new ArrayList<>();
        this.owner = owner;
        this.name = name;

        //TODO : Create lobby executor
    }

    //-------------------------GET-------------------------

    public int getUdpPort() {
        return udpPort;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public LobbyConnection getLobbyConnection() {
        return lobbyConnection;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public boolean isRunning() {
        return running;
    }

    public LobbyState getState() {
        return state;
    }

    private Lobby getInstance() {
        return this;
    }

    //-----------------------------------------------------
    //------------------------SET--------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    //-----------------------------------------------------
    //------------------------GAME-------------------------
    public void startGame(String mapID) {
        gameManager = new GameManager(this, mapID);

        open = false;
        state = LobbyState.PLAYING;
    }

    //Update cycle is in LobbyExecutor

    public void stopGame() {
        //Broadcast end of game
        EndGamePacket p = new EndGamePacket();
        broadcastToAll(false, p);

        players.forEach(pl -> pl.setEntity(null));//Lose game entity reference

        open = true;
        state = LobbyState.WAITING;
    }
    //-----------------------------------------------------

    public void addPlayer(Player player) {
        players.add(player);
        player.joinLobby(this);
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void removePlayer(Player player) {
        players.remove(player);
        if (players.size() <= 0) {
            // There is no more players. Closing lobby
            close();
            return;
        }

        player.setEntity(null);//If he was in game, lose entity reference

        if (owner.equals(player))
            owner = players.stream().findAny().get(); // Change owner if the player was online
    }

    public void disconnectAll() {
        for (Player p : players)
            removePlayer(p);
    }

    public void close() {
        disconnectAll();
        lobbyConnection.close();
        running = false;
    }

    /**
     * Broadcasts packet(s) to all players
     *
     * @param udp     Using UDP or TCP
     * @param packets to send
     */
    public void broadcastToAll(boolean udp, Packet... packets) {
        if (udp)
            players.stream().filter(p -> p.getJoinedLobbyState() == LobbyJoiningState.CONNECTED).forEach(p -> lobbyConnection.send(p, packets));
        else
            players.forEach(p -> p.serverConnection.send(packets));
    }

    /**
     * Broadcasts packet to players specified
     *
     * @param udp     Using UDP or TCP
     * @param packet to send
     * @param playersT players targeted by broadcast
     */
    public void broadcastTo(boolean udp, Packet packet, Player... playersT) {
        if (udp)
            Arrays.stream(playersT).filter(p -> p.getJoinedLobbyState() == LobbyJoiningState.CONNECTED).forEach(p -> lobbyConnection.send(p, packet));
        else
            Arrays.stream(playersT).forEach(p -> p.serverConnection.send(packet));
    }

    /**
     * Broadcasts packet(s) to all players except one
     *
     * @param udp     Using UDP or TCP
     * @param player  not to send the packet(s)
     * @param packets to send
     */
    public void broadcastExcept(boolean udp, Player player, Packet... packets) {
        if (udp)
            players.stream().filter(p -> p.getJoinedLobbyState() == LobbyJoiningState.CONNECTED).filter(p -> !player.equals(p)).forEach(p -> lobbyConnection.send(p, packets));
        else
            players.stream().filter(p -> !player.equals(p)).forEach(p -> p.serverConnection.send(packets));
    }

    //-----------------------------------------------------

    class LobbyExecutor extends Thread {

        private long lastTick = System.nanoTime();
        private long currentTick = 0;

        @Override
        public void run() {
            while (running) {
                if (System.nanoTime() - lastTick < 5e7) {
                    if (currentTick == 20) {
                        LobbyInfoPacket lobbyInfoPacket = new LobbyInfoPacket(getInstance());
                        broadcastToAll(true, lobbyInfoPacket);
                    }

                    //-------GAME-------
                    if(state == LobbyState.PLAYING){
                        gameManager.Update();
                        if (currentTick == 20) //Should we change this to a different tick rate?
                            gameManager.UpdatePlayersPos();
                    }
                    //------------------

                    if (currentTick >= 20)
                        currentTick = 0;
                    else
                        currentTick++;
                    lastTick = System.nanoTime();
                }
            }
        }
    }

}