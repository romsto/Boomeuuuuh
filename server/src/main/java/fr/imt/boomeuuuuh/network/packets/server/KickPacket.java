package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class KickPacket extends Packet {

    private final String reason;

    /**
     * Packet to inform the client it has been kicked from a lobby
     * @param reason reason it has been kicked
     */
    public KickPacket(String reason) {
        super(PacketType.KICK);

        this.reason = reason;
    }

    @Override
    protected byte[] encode() {
        return reason.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
