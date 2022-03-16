package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Location;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class PlayerReferencePacket extends Packet {

    private final int entityId;
    private final String playerName;

    public PlayerReferencePacket(int entityId, String playerName) {
        super(PacketType.PLAYER_REFERENCE);
        this.entityId = entityId;
        this.playerName = playerName;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.writeBytes(playerName.getBytes(StandardCharsets.UTF_8));
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
