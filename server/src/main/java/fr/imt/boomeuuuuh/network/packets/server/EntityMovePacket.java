package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Location;

import java.io.ByteArrayOutputStream;

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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.writeBytes(location.toByteArray());
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
