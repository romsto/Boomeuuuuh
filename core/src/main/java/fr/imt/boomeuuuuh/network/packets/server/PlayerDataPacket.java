package fr.imt.boomeuuuuh.network.packets.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.PlayerData;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.client.SelectSkinPacket;
import fr.imt.boomeuuuuh.network.packets.client.UnlockSkinPacket;
import fr.imt.boomeuuuuh.screens.ScreenType;
import fr.imt.boomeuuuuh.screens.SkinsScreen;
import fr.imt.boomeuuuuh.screens.StatsScreen;
import fr.imt.boomeuuuuh.utils.AssetsManager;

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

        final int gold = Ints.fromByteArray(bgold);
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

        final PlayerData playerData = MyGame.getInstance().playerData;

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
        if (MyGame.getInstance().getCurrentScreenType() == ScreenType.SKINS) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
//                    Skin skin = AssetsManager.getUISkin();
//                    Table skinTable = ((SkinsScreen) MyGame.getInstance().getCurrentScreen()).skinTable;
//                    ((SkinsScreen) MyGame.getInstance().getCurrentScreen()).act = true;
//                    skinTable.clear();
//                    for (final fr.imt.boomeuuuuh.utils.Skin value : fr.imt.boomeuuuuh.utils.Skin.values()) {
//                        skinTable.row().pad(10, 0, 10, 0);
//                        boolean hasSkin = playerData.hasSkin(value);
//                        TextButton button;
//                        if (hasSkin) {
//                            if (playerData.getCurrentSkin() == value) {
//                                button = new TextButton("Current Skin", skin, "maroon");
//                            } else {
//                                button = new TextButton("Select Skin", skin);
//                                button.addListener(new ChangeListener() {
//                                    @Override
//                                    public void changed(ChangeEvent event, Actor actor) {
//                                        if (!((SkinsScreen) MyGame.getInstance().getCurrentScreen()).act) return;
//                                        MyGame.getInstance().serverConnection.send(new SelectSkinPacket(value.getDataName()));
//                                        ((SkinsScreen) MyGame.getInstance().getCurrentScreen()).act = false;
//                                    }
//                                });
//                            }
//                        } else {
//                            if (playerData.gold >= 100) {
//                                button = new TextButton("100 gold", skin);
//                                button.addListener(new ChangeListener() {
//                                    @Override
//                                    public void changed(ChangeEvent event, Actor actor) {
//                                        if (!((SkinsScreen) MyGame.getInstance().getCurrentScreen()).act) return;
//                                        MyGame.getInstance().serverConnection.send(new UnlockSkinPacket(value.getDataName()));
//                                        ((SkinsScreen) MyGame.getInstance().getCurrentScreen()).act = false;
//                                    }
//                                });
//                            } else {
//                                button = new TextButton("Not enough gold", skin);
//                            }
//                        }
//
//                        skinTable.add(value.getIcon()).width(32).height(32);
//                        skinTable.add(button).fillX().uniformX();
//                        ((SkinsScreen) MyGame.getInstance().getCurrentScreen()).gold.setText(gold + " gold");
//                    }
                    ((SkinsScreen) MyGame.getInstance().getCurrentScreen()).reShow();
                }
            });
        }
        if(MyGame.getInstance().getCurrentScreenType() == ScreenType.STATS){
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    ((StatsScreen) MyGame.getInstance().getCurrentScreen()).reShow();
                }});
        }
    }
}
