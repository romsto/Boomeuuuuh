package fr.imt.boomeuuuuh.network.packets.client;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class InitializeLobbyConnectionPacket extends Packet {

    private final int port;

    public InitializeLobbyConnectionPacket(int port) {
        super(PacketType.INITIALIZE_LOBBY_CONNECTION);

        this.port = port;
    }

    @Override
    protected byte[] encode() {
        return Ints.toByteArray(port);
    }

    @Override
    public void handle() {
        // Shouldn't be handled client-side
    }
}
