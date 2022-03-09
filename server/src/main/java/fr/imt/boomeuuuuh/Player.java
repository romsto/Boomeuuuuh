package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.ServerConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Player {

    private int id;
    private String name;
    private boolean authentified = false;

    private Lobby lobby;

    private final InetAddress address;
    private final Socket serverSocket;
    public final ServerConnection serverConnection;
    private int udpPort;

    public Player(Socket socket) throws IOException {
        this.serverSocket = socket;
        this.address = socket.getInetAddress();

        this.serverConnection = new ServerConnection(this, socket);
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getUDPPort() {
        return udpPort;
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
}
