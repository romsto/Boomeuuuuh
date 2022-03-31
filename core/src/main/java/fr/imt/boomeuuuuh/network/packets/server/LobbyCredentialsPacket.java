package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.LobbyConnection;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.net.SocketException;

public class LobbyCredentialsPacket extends Packet {

    private final int port;

    public LobbyCredentialsPacket(int port) {
        super(PacketType.LOBBY_CREDENTIALS);
        this.port = port;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        try {
            MyGame.getInstance().lobby.lobbyConnection = new LobbyConnection(MyGame.SERVER_ADDRESS, port);
        } catch (SocketException e) {
            e.printStackTrace();
            // TODO Manage this error
        }
    }
}
