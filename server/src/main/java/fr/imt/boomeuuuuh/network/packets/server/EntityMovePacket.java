package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Location;

import java.io.ByteArrayOutputStream;

public class EntityMovePacket extends Packet {

    private final int entityId;
    private final Location location;
    private final int speed;

    /**
     * Packet to tell the client to move an entity
     * @param entityId ID of the entity
     * @param location new location of the entity
     * @param speed speed at which the entity moves
     */
    public EntityMovePacket(int entityId, Location location, int speed) {
        super(PacketType.ENTITY_MOVE);
        this.entityId = entityId;
        this.location = location;
        this.speed = speed;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.writeBytes(location.toByteArray());
        baos.writeBytes(Ints.toByteArray(speed));
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
