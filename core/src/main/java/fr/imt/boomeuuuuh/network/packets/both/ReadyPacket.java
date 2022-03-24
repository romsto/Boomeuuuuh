package fr.imt.boomeuuuuh.network.packets.both;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class ReadyPacket extends Packet {

    public ReadyPacket() {
        super(PacketType.READY);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        // TODO Handle this packet
    }
}
