package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class StartGamePacket extends Packet {

    public StartGamePacket() {
        super(PacketType.START_GAME);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
