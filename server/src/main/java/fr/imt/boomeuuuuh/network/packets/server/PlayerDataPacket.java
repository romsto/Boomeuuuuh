package fr.imt.boomeuuuuh.network.packets.server;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.players.PlayerData;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class PlayerDataPacket extends Packet {

    private final Player player;

    /**
     * Packet containing stats of a player (gold, level, kills, max kill streak, wins, name, current skin, owned skins)
     * @param player player the packet is about
     */
    public PlayerDataPacket(Player player) {
        super(PacketType.PLAYER_DATA);
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        PlayerData data = player.getPlayerData();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(data.getGold()));
        baos.writeBytes(Ints.toByteArray(data.getLevel()));
        baos.writeBytes(Ints.toByteArray(data.getKills()));
        baos.writeBytes(Ints.toByteArray(data.getMaxkillstreak()));
        baos.writeBytes(Ints.toByteArray(data.getWins()));
        StringBuilder builder = new StringBuilder(player.getName()).append("|").append(data.getCurrentSkin()).append("|");
        String delimiter = "";
        for (String unlockedSkin : data.getUnlockedSkins()) {
            builder.append(delimiter).append(unlockedSkin);
            delimiter = "/";
        }
        baos.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
