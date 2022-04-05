package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.Location;

public class PlayerChangeBlocPacket extends Packet {

    private final Location newLocation;

    public PlayerChangeBlocPacket(Location newLocation) {
        super(PacketType.CHANGE_BLOC);
        this.newLocation = newLocation;
    }

    @Override
    protected byte[] encode() {
        return newLocation.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled client-side
    }
}
