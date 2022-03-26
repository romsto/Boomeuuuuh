package fr.imt.boomeuuuuh.network.packets.both;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class DeclinePacket extends Packet {

    private final String reason;

    public DeclinePacket(String reason) {
        super(PacketType.DECLINE);
        this.reason = reason;
    }

    @Override
    protected byte[] encode() {
        return reason.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        System.out.println(reason);
    }
}
