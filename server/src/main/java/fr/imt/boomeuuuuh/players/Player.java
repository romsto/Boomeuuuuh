package fr.imt.boomeuuuuh.players;

import fr.imt.boomeuuuuh.lobbies.LobbyJoiningState;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.server.KickPacket;
import fr.imt.boomeuuuuh.network.packets.server.LobbyCredentialsPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Player {

    private int id;
    private String name;
    private boolean authentified = false;
    private boolean online = true;

    private Lobby lobby;
    private LobbyJoiningState joinedLobby = LobbyJoiningState.DISCONNETED;
    private int port;

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

        return id == player.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

