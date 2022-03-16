package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class SuccessfullyJoinedPacket extends Packet {

    public SuccessfullyJoinedPacket() {
        super(PacketType.SUCCESSFULLY_JOINED);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        // Shouldn't be handled server side
    }
}
