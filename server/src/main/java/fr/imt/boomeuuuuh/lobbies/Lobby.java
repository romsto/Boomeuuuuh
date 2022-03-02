package fr.imt.boomeuuuuh.lobbies;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.LobbyConnection;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Lobby {

    private final LobbyConnection lobbyConnection;
    private final int udpPort;
    private final Collection<Player> players;
    private Player owner;
    private String name;

    public Lobby(int udpPort, String name, Player owner) throws SocketException {
        this.udpPort = udpPort;
        this.lobbyConnection = new LobbyConnection(udpPort);
        this.players = new ArrayList<>();
        this.owner = owner;
        this.name = name;

        addPlayer(owner);
    }

    public int getUdpPort() {
        return udpPort;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public LobbyConnection getLobbyConnection() {
        return lobbyConnection;
    }

    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void addPlayer(Player player) {

    }

    public void removePlayer(Player player) {

    }
}
