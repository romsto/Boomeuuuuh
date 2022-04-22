package fr.imt.boomeuuuuh.players;

import fr.imt.boomeuuuuh.Boomeuuuuh;

import java.util.ArrayList;

public class PlayerData {

    private Player player;
    private int gold = 0;
    private int level = 0;
    private int kills = 0;
    private int maxkillstreak = 0;
    private int wins = 0;
    private String currentSkin;
    private ArrayList<String> unlockedSkins = new ArrayList<>();

    /**
     * Class to store player data outside the database
     * @param gold gold the player has
     * @param level level of the player
     * @param currentSkin name of the currentSkin of the player
     * @param unlockedSkins names of the player's unlocked skins
     * @param kills total kills of the player
     * @param maxkillstreak max kill streak of the player
     * @param wins total wins of the player
     */
    public PlayerData(int gold, int level, String currentSkin, ArrayList<String> unlockedSkins, int kills, int maxkillstreak, int wins) {
        this.gold = gold;
        this.level = level;
        this.kills = kills;
        this.maxkillstreak = maxkillstreak;
        this.wins = wins;
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


    public int getKills() {
        return kills;
    }

    public void addKills(int kills) {
        this.kills += kills;
        Boomeuuuuh.database.setKills(player.getName(), this.kills);
    }

    public int getMaxkillstreak() {
        return maxkillstreak;
    }

    public void setMaxkillstreak(int maxkillstreak) {
        this.maxkillstreak = maxkillstreak;
        Boomeuuuuh.database.setMaxKillstreak(player.getName(), maxkillstreak);
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins++;
        Boomeuuuuh.database.setWins(player.getName(), this.wins);
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
