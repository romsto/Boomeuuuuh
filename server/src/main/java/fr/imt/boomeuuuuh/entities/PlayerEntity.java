package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;

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

    @Override
    public void setPos(Location pos) {
        super.setPos(pos);

        GameManager manager = playerRep.getLobby().getGameManager();
        if (manager == null)
            return;
        for (Entity entity : new ArrayList<>(manager.getEntityList())) {
            if (!(entity instanceof PowerUp))
                continue;
            if (!entity.getPos().equals(pos))
                continue;

            playerRep.maxBombs++;
            playerRep.changed = true;
            manager.destroyEntity(entity);
            break;
        }
    }
}
