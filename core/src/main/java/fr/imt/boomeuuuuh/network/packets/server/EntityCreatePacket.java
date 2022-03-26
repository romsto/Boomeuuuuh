package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.Location;

public class EntityCreatePacket extends Packet {

    private final int entityId, entityType;
    private final Location location;

    public EntityCreatePacket(int entityId, int entityType, Location location) {
        super(PacketType.ENTITY_CREATE);
        this.entityId = entityId;
        this.entityType = entityType;
        this.location = location;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        // TODO
    }
}
