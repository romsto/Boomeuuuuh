package fr.imt.boomeuuuuh.network.packets.both;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class DeclinePacket extends Packet {

    private final String reason;
    private final Player player;

    public DeclinePacket(String reason) {
        super(PacketType.DECLINE);
        this.reason = reason;
        this.player = null;
    }

    public DeclinePacket(String reason, Player player) {
        super(PacketType.DECLINE);
        this.reason = reason;
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        return reason.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // TODO
    }
}
