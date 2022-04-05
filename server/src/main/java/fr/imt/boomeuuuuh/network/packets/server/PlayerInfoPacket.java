package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

import java.io.ByteArrayOutputStream;

public class PlayerInfoPacket extends Packet {

    private final Player player;

    public PlayerInfoPacket(Player player) {
        super(PacketType.PLAYER_INFO);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baous = new ByteArrayOutputStream();
        baous.writeBytes(Ints.toByteArray(player.maxBombs));
        baous.writeBytes(Ints.toByteArray(player.bombPower));
        baous.writeBytes(Ints.toByteArray(player.speed));
        baous.writeBytes(Ints.toByteArray(player.getEntity().getKills()));
        return baous.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
