package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.players.Player;

public class PlayerEntity extends DynamicEntity {

    private final Player playerRep;

    private int kills = 0;

    public PlayerEntity(Player playerRepresented, int id) {
        super(id);

        playerRep = playerRepresented;
    }

    public Player getPlayer() {
        return playerRep;
    } //The big faille

    public void addKill() {
        kills++;
    }

    public int getKills() {
        return kills;
    }
}
