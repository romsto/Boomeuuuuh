package fr.imt.boomeuuuuh.network.packets.both;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class AlivePacket extends Packet {

    public AlivePacket() {
        super(PacketType.ALIVE);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        // Do nothing, just to avoid timeout
    }
}
