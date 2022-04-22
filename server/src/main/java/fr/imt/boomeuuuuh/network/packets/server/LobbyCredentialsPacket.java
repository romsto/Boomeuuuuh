package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class LobbyCredentialsPacket extends Packet {

    private final int port;

    /**
     * Information regarding what UDP port the client can contact the current lobby on
     * @param port port the lobby communicates on
     */
    public LobbyCredentialsPacket(int port) {
        super(PacketType.LOBBY_CREDENTIALS);
        this.port = port;
    }

    @Override
    protected byte[] encode() {
        return Ints.toByteArray(port);
    }

    @Override
    public void handle() {
        // Shouldn't be handled server side
    }
}
