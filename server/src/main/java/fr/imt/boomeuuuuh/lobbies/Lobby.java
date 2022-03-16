package fr.imt.boomeuuuuh.lobbies;

import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.LobbyConnection;
import fr.imt.boomeuuuuh.network.packets.Packet;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;

//Class for a lobby
//  -> Repertories players connected to it (and the subsequent game)
//  -> Manages UDP communications with the players
//  -> Manages information passed between players - eg. player skin that has to be relayed
//  -> Manages Chat

public class Lobby {

    public boolean closed = false;

    private final LobbyConnection lobbyConnection;
    private final int udpPort;
    private final int lobbyID;

    private final Collection<Player> players;
    private Player owner;
    private String name;
    private boolean open = true;

    public Lobby(int lobbyID, String name, Player owner) throws SocketException {
        this.lobbyConnection = new LobbyConnection();
        this.udpPort = lobbyConnection.getPort();
        this.lobbyID = lobbyID;

        this.players = new ArrayList<>();
        this.owner = owner;
        this.name = name;

        addPlayer(owner);
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

    //-----------------------------------------------------
    //------------------------SET--------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.joinLobby(this);
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void removePlayer(Player player) {
        player.leaveLobby("Lobby closed"); // TODO Change reason here
        players.remove(player);
    }

    public void disconnectAll() {
        for (Player p : players)
            removePlayer(p);
    }

    /**
     * Broadcasts packet(s) to all players
     *
     * @param udp     Using UDP or TCP
     * @param packets to send
     */
    public void broadcastToAll(boolean udp, Packet... packets) {
        if (udp)
            players.forEach(p -> lobbyConnection.send(p, packets));
        else
            players.forEach(p -> p.serverConnection.send(packets));
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
            players.stream().filter(p -> !player.equals(p)).forEach(p -> lobbyConnection.send(p, packets));
        else
            players.stream().filter(p -> !player.equals(p)).forEach(p -> p.serverConnection.send(packets));
    }
    //-----------------------------------------------------

    private class LobbyThread extends Thread {

        long lastUpdate = System.nanoTime();

        @Override
        public void run() {
            long diff = System.nanoTime() - lastUpdate;


            long lastUpdate = System.nanoTime();
            if (!closed)
                run();
        }
    }
}