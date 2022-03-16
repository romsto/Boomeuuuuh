package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.server.KickPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Player {

    private int id;
    private String name;
    private boolean authentified = false;

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

    public LobbyJoiningState getJoinedLobby() {
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
        if (isInLobby())
            leaveLobby("Already in a lobby");
        this.lobby = lobby;
        this.joinedLobby = LobbyJoiningState.WAITING_PORT;
        // TODO send Lobby info packet
    }

    public void leaveLobby(String reason) {
        KickPacket kickPacket = new KickPacket(reason);
        getLobby().getLobbyConnection().send(this, kickPacket);
        lobby = null;
        joinedLobby = LobbyJoiningState.DISCONNETED;
    }

    public void disconnect() {
        if (isInLobby()) {
            // TODO leave lobby
        }
        serverConnection.close();
    }
}

