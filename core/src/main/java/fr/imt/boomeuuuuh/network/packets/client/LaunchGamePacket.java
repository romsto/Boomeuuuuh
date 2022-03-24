package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class LaunchGamePacket extends Packet {

    public LaunchGamePacket() {
        super(PacketType.LAUNCH_GAME);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        // Shouldn't be handled client-side
    }
}
