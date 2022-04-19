package fr.imt.boomeuuuuh.network.packets.server;

import com.badlogic.gdx.Gdx;
import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.PlayerData;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PlayerDataPacket extends Packet {

    private final byte[] rawData;

    public PlayerDataPacket(byte[] rawData) {
        super(PacketType.PLAYER_DATA);
        this.rawData = rawData;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        byte[] bgold = Arrays.copyOfRange(rawData, 0, 4);
        byte[] blevel = Arrays.copyOfRange(rawData, 4, 8);
        byte[] bkills = Arrays.copyOfRange(rawData, 8, 12);
        byte[] bmaxkillstreak = Arrays.copyOfRange(rawData, 12, 16);
        byte[] bwins = Arrays.copyOfRange(rawData, 16, 20);
        byte[] bstring = Arrays.copyOfRange(rawData, 20, rawData.length);

        int gold = Ints.fromByteArray(bgold);
        int level = Ints.fromByteArray(blevel);
        int kills = Ints.fromByteArray(bkills);
        int maxkillstreak = Ints.fromByteArray(bmaxkillstreak);
        int wins = Ints.fromByteArray(bwins);
        String string = new String(bstring, StandardCharsets.UTF_8);
        String[] split = string.split("[|]");
        String username = split[0];
        String currentSkin = split[1];
        String[] skins = split[2].split("/");

        if (!MyGame.getInstance().logged) {
            MyGame.getInstance().logged = true;
            MyGame.getInstance().playerData = new PlayerData();
            MyGame.getInstance().username = username;
        }

        PlayerData playerData = MyGame.getInstance().playerData;

        playerData.gold = gold;
        playerData.level = level;
        playerData.kills = kills;
        playerData.maxkillstreak = maxkillstreak;
        playerData.wins = wins;
        playerData.currentSkin = currentSkin;
        playerData.unlockedSkins = skins;

        if (MyGame.getInstance().getCurrentScreenType() == ScreenType.LOG_IN)
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    MyGame.getInstance().changeScreen(ScreenType.LOBBY_SELECTION);
                }
            });
    }
}
