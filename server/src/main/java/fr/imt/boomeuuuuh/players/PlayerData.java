package fr.imt.boomeuuuuh.players;

import fr.imt.boomeuuuuh.Boomeuuuuh;

import java.util.ArrayList;

public class PlayerData {

    private Player player;
    private int gold;
    private int level;
    private int kills;
    private int maxkillstreak;
    private int wins;
    private String currentSkin;
    private ArrayList<String> unlockedSkins;

    /**
     * Class to store player data outside the database
     *
     * @param gold          gold the player has
     * @param level         level of the player
     * @param currentSkin   name of the currentSkin of the player
     * @param unlockedSkins names of the player's unlocked skins
     * @param kills         total kills of the player
     * @param maxkillstreak max kill streak of the player
     * @param wins          total wins of the player
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

    /**
     * Get current player assigned to the data
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player referred to the data
     *
     * @param player Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the player's balance
     *
     * @return golds
     */
    public int getGold() {
        return gold;
    }

    /**
     * Sets the player balance
     *
     * @param gold current
     */
    public void setGold(int gold) {
        this.gold = gold;
        Boomeuuuuh.database.setGold(player.getName(), gold);
    }

    /**
     * Adds a specific amount of gold
     *
     * @param difference to change
     */
    public void addGold(int difference) {
        setGold(gold += difference);
    }

    /**
     * Get the player level
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the player current level
     *
     * @param level to set
     */
    public void setLevel(int level) {
        this.level = level;
        Boomeuuuuh.database.setLevel(player.getName(), level);
    }

    /**
     * Get amount of kills
     *
     * @return kills
     */
    public int getKills() {
        return kills;
    }

    /**
     * Add one or more kill to player's kills
     *
     * @param kills difference
     */
    public void addKills(int kills) {
        this.kills += kills;
        Boomeuuuuh.database.setKills(player.getName(), this.kills);
    }

    /**
     * Get the player maxkillstreak
     *
     * @return max killstreak
     */
    public int getMaxkillstreak() {
        return maxkillstreak;
    }

    /**
     * Set the player max killstreak
     *
     * @param maxkillstreak to set
     */
    public void setMaxkillstreak(int maxkillstreak) {
        this.maxkillstreak = maxkillstreak;
        Boomeuuuuh.database.setMaxKillstreak(player.getName(), maxkillstreak);
    }

    /**
     * Get player's wins
     *
     * @return wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Adds one win to player's wins
     */
    public void addWin() {
        this.wins++;
        Boomeuuuuh.database.setWins(player.getName(), this.wins);
    }

    /**
     * Adds one level to player's levels
     */
    public void addOneLevel() {
        setLevel(level + 1);
    }

    /**
     * Get the selected skin of the player
     *
     * @return selected skin
     */
    public String getCurrentSkin() {
        return currentSkin;
    }

    /**
     * Set current skin used
     *
     * @param currentSkin used
     */
    public void setCurrentSkin(String currentSkin) {
        this.currentSkin = currentSkin;
        Boomeuuuuh.database.setCurrentSkin(player.getName(), currentSkin);
    }

    /**
     * Get the list of all unlocked skins
     *
     * @return skins list
     */
    public ArrayList<String> getUnlockedSkins() {
        return unlockedSkins;
    }

    /**
     * Check if the player as a skin
     *
     * @param skin to check
     * @return boolean
     */
    public boolean hasSkin(String skin) {
        return unlockedSkins.contains(skin);
    }

    /**
     * Unlock a skin if the player has enough gold
     *
     * @param skin to unlock
     */
    public void unlockSkin(String skin) {
        unlockedSkins.add(skin);
        Boomeuuuuh.database.unlockSkin(player.getName(), skin);
    }
}
