package fr.imt.boomeuuuuh.lobbies;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.LobbyConnection;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//Class for a lobby
//  -> Repertories players connected to it (and the subsequent game)
//  -> Manages UDP communications with the players
//  -> Manages information passed between palyers - eg. player skin that has to be relayed
//  -> Manages Chat

public class Lobby {

    private final LobbyConnection lobbyConnection;
    private final int udpPort;
    private final int lobbyID;

    //Player Variables
    private final Collection<Player> players;
    private Player owner;
    private String name;

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

    public int getLobbyID(){ return lobbyID; }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
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

        //TODO : relay info to all client lobbys (TCP)
    }

    //After connection is accepted by the lobby
    //  player receives info that it has connected to the lobby and receives the lobby info
    public void sendLobbyInfo(Player player){
        //TODO : send lobby info to player (TCP)
    }

    public void removePlayer(Player player) {
        players.remove(player);

        //TODO : relay info to all client lobbys (TCP)
    }
    //-----------------------------------------------------
    //--------------------Relay Methods--------------------
    //  -> Used for communication

    //DISCONNECT (TCP)
    //Relay disconnect to all players
    public void disconnectAll(){
        for (Player p : players)
            disconnectPlayer(p);
    }

    public void disconnectPlayer(Player p){
        //TODO : Send player the order to disconnect
        players.removeIf(pl -> pl.getId() == p.getId());
    }

    //CHAT (TCP)
    //Relay chat from a player to all others
    public void relayChat(List<String> chat, Player origin){
        for (Player p:players) {
            if (p != origin)
                relayChat(chat, origin, p);
        }
    }
    //Relay chat from a player to another
    public void relayChat(List<String> chat, Player origin, Player target){
        //TODO : do chat relay through players
    }

    //PLAYER INFO (TCP)
    //Relay player specific info to all other players (player skin, name, stats)
    public void relayPlayerInfo(Player origin){
        for (Player p:players) {
            if (p != origin)
                relayPlayerInfo(origin, p);
        }
    }
    //Relay player specific info to a target player
    public void relayPlayerInfo(Player origin, Player target){
        //TODO : Relay info through player
    }

    //PLAYER POSITIONS (TCP)
    //Send position of all players
    public void relayTCPPositions(List<PlayerPos> pos, Player target){
        //TODO : Relay packet through player
    }
    //PLAYER POSITIONS (UDP)
    //Send position updates to all players
    public void relayUpdatedPositions(List<PlayerPos> pos){
        for (Player p:players) {
            relayUpdatedPositions(pos, p);
        }
    }
    //Send position updates to one player
    public void relayUpdatedPositions(List<PlayerPos> pos, Player target){
        //TODO : Send packet through TCP
    }

    //BOMBS (TCP)
    //  EXPLODE
    public void sendExplosion(){}
    //  PLACE
    public void relayPlaceBomb(){}
    //-----------------------------------------------------
}

class PlayerPos{ //TODO : Convert this shit to packets
    public Player player;
    public int posx;
    public int posy;
}