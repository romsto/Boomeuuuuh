package fr.imt.boomeuuuuh.players;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.Server;
import fr.imt.boomeuuuuh.entities.PlayerEntity;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyJoiningState;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.server.KickPacket;
import fr.imt.boomeuuuuh.network.packets.server.LobbyCredentialsPacket;
import fr.imt.boomeuuuuh.network.packets.server.PlayerDataPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Player {

    private int id;
    private String name;
    private PlayerData playerData;
    private boolean authentified = false;
    private boolean online = true;

    private Lobby lobby;
    private LobbyJoiningState joinedLobby = LobbyJoiningState.DISCONNETED;
    private int port;

    public int maxBombs = 3;
    public int bombPower = 3;
    public int speed = 1;
    public int currentBombs = 0;

    private final InetAddress address;
    private final Socket serverSocket;
    public final ServerConnection serverConnection;

    public Player(Socket socket) throws IOException {
        this.serverSocket = socket;
        this.address = socket.getInetAddress();

        this.serverConnection = new ServerConnection(this, socket);
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public LobbyJoiningState getJoinedLobbyState() {
        return joinedLobby;
    }

    public boolean isAuthentified() {
        return authentified;
    }

    public boolean authenticate(String username, String password) {
        if (Server.getPlayers().stream().anyMatch(player -> player.isAuthentified() && player.getName().equalsIgnoreCase(username))) {
            serverConnection.send(new DeclinePacket("You are already connected..."));
            return false;
        }
        boolean success = Boomeuuuuh.database.login(username, password);
        if (success) {
            authentified = true;
            playerData = Boomeuuuuh.database.getPlayerData(username);
            playerData.setPlayer(this);
            name = username;

            this.serverConnection.send(new PlayerDataPacket(this));
            return true;
        }
        serverConnection.send(new DeclinePacket("Wrong credentials..."));
        return false;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setJoinedLobbyState(LobbyJoiningState state) {
        this.joinedLobby = state;
    }

    public boolean isInLobby() {
        return lobby != null && joinedLobby != LobbyJoiningState.DISCONNETED;
    }

    public void joinLobby(Lobby lobby) {
        if (isInLobby()) {
            leaveLobby("Already in a lobby");
        }
        this.lobby = lobby;
        this.joinedLobby = LobbyJoiningState.WAITING_PORT;
        serverConnection.send(new LobbyCredentialsPacket(lobby.getUdpPort()));
    }

    public void leaveLobby(String reason) {
        KickPacket kickPacket = new KickPacket(reason);
        getLobby().getLobbyConnection().send(this, kickPacket);
        lobby.removePlayer(this);
        lobby = null;
        joinedLobby = LobbyJoiningState.DISCONNETED;
    }

    public void disconnect() {
        if (isInLobby())
            lobby.removePlayer(this);
        online = false;
        serverConnection.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equalsIgnoreCase(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    //-------------------------GAME------------------------
    PlayerEntity myEntity;

    public PlayerEntity getEntity() {
        return myEntity;
    }

    public void setEntity(PlayerEntity e) {
        myEntity = e;
    }
    //-----------------------------------------------------
}

