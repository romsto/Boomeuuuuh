package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.Location;

public class BombPlacePacket extends Packet {

    private final Location location;

    public BombPlacePacket(Location location) {
        super(PacketType.BOMB_PLACE);
        this.location = location;
    }

    @Override
    protected byte[] encode() {
        return location.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled client side
    }
}
