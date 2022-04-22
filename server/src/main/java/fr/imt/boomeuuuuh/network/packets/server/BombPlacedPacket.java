package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Location;

import java.io.ByteArrayOutputStream;

public class BombPlacedPacket extends Packet {

    private final int entityId, power;
    private final Location location;

    /**
     * Packet to tell the target client that a bomb has been placed in game
     * @param entityId ID assigned to the bomb
     * @param power power of the bomb
     * @param location location the bomb was placed
     */
    public BombPlacedPacket(int entityId, int power, Location location) {
        super(PacketType.BOMB_PLACED);
        this.entityId = entityId;
        this.power = power;
        this.location = location;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.write(power);
        baos.writeBytes(location.toByteArray());
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
