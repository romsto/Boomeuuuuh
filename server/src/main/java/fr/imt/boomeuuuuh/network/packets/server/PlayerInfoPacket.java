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
        baous.writeBytes(Ints.toByteArray(player.getMaxBombs()));
        baous.writeBytes(Ints.toByteArray(player.getBombPower()));
        baous.writeBytes(Ints.toByteArray(player.getSpeed()));
        baous.writeBytes(Ints.toByteArray(player.getGameKills()));
        return baous.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
