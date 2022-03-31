package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.Location;

public class EntityMovePacket extends Packet {

    private final int entityId;
    private final Location location;

    public EntityMovePacket(int entityId, Location location) {
        super(PacketType.ENTITY_MOVE);
        this.entityId = entityId;
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
