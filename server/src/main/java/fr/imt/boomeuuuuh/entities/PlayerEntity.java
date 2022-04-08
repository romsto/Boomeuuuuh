package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Random;

public class PlayerEntity extends DynamicEntity {

    private final Player playerRep;
    private static final Random random = new Random();

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

            int rd = random.nextInt(10);
            if (rd <= 1)
                playerRep.bombPower++;
            else if (rd <= 7)
                playerRep.maxBombs++;
            else
                playerRep.speed++;

            playerRep.changed = true;
            manager.destroyEntity(entity);
            break;
        }
    }
}
