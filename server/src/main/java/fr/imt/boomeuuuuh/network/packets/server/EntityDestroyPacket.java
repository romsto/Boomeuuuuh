package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.io.ByteArrayOutputStream;

public class EntityDestroyPacket extends Packet {

    private final int entityId;

    /**
     * Packet to tell the client of the destruction of an entity
     * @param entityId ID of the entity destroyed
     */
    public EntityDestroyPacket(int entityId) {
        super(PacketType.ENTITY_DESTROY);
        this.entityId = entityId;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
