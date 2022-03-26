package fr.imt.boomeuuuuh.players;

import fr.imt.boomeuuuuh.Boomeuuuuh;

import java.util.ArrayList;

public class PlayerData {

    private Player player;
    private int gold = 0;
    private int level = 0;
    private String currentSkin;
    private ArrayList<String> unlockedSkins = new ArrayList<>();

    public PlayerData(int gold, int level, String currentSkin, ArrayList<String> unlockedSkins) {
        this.gold = gold;
        this.level = level;
        this.currentSkin = currentSkin;
        this.unlockedSkins = unlockedSkins;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
        Boomeuuuuh.database.setGold(player.getName(), gold);
    }

    public void addGold(int difference) {
        setGold(gold += difference);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        Boomeuuuuh.database.setLevel(player.getName(), level);
    }

    public void addOneLevel() {
        setLevel(level + 1);
    }

    public String getCurrentSkin() {
        return currentSkin;
    }

    public void setCurrentSkin(String currentSkin) {
        this.currentSkin = currentSkin;
        Boomeuuuuh.database.setCurrentSkin(player.getName(), currentSkin);
    }

    public ArrayList<String> getUnlockedSkins() {
        return unlockedSkins;
    }

    public boolean hasSkin(String skin) {
        return unlockedSkins.contains(skin);
    }

    public void unlockSkin(String skin) {
        unlockedSkins.add(skin);
        Boomeuuuuh.database.unlockSkin(player.getName(), skin);
    }
}
