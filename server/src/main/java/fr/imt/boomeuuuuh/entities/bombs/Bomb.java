package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.entities.*;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Bomb extends DynamicEntity {

    //Chara Variables
    private static final long delay = 3000; //ms
    //Explosion
    //private final ExplosionShape expShape;
    private final ClassicShape classicShape;

    //Time
    private final long endTime;
    public boolean calculating = false;
    private int power = 3;

    private final Player parentPlayer;

    public Bomb(int id, Player parentPlayer) {
        super(id);

        //SetTime
        endTime = System.currentTimeMillis() + delay;

        this.power = parentPlayer.bombPower;

        //Create explosion shape
        //expShape = new ExplosionShape(power, power, power, power);
        classicShape = new ClassicShape(power);

        //SetPlayer
        this.parentPlayer = parentPlayer;
    }

    //-------------------------GET-------------------------
    public int getPower() {
        return power;
    }

    public PlayerEntity getPlayerEntity() {
        return parentPlayer.getEntity();
    }
    //-----------------------------------------------------

    public void checkExplosion(Collection<Entity> entityList, GameManager manager) {
        if (endTime < System.currentTimeMillis())
            Explode(entityList, manager);
    }

    public void forceExplode(Collection<Entity> entityList, GameManager manager) {
        Explode(entityList, manager);
    }

    /*private void Explode(Collection<Entity> entityList, GameManager manager){
        calculating = true;
        try {
            for (Entity e : expShape.calcExplosion(entityList, manager.getMapHeight(), manager.getMapWidth(), pos.getX(), pos.getY())) {
                if (e instanceof Bomb)
                    if (((Bomb) e).calculating)
                        ((Bomb) e).forceExplode(entityList, manager);
                if (e instanceof SoftBlock || e instanceof PowerUp)
                    manager.destroyEntity(e);
                if (e instanceof PlayerEntity) {
                    parentPlayer.getEntity().addKill((PlayerEntity) e); //Add kill to parent player
                    manager.destroyEntity(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        manager.destroyEntity(this);
        parentPlayer.currentBombs--;
    }*/

    private void Explode(Collection<Entity> entityList, GameManager manager) {
        List<Entity> explosion = classicShape.calcExplosion(entityList, this, manager.getMapHeight(), manager.getMapWidth(), pos.getX(), pos.getY());
        List<Entity> copied = new ArrayList<>(entityList);
        copied.remove(this);
        copied.removeAll(explosion);
        for (Entity entity : explosion) {
            if (entity instanceof Bomb)
                ((Bomb) entity).forceExplode(copied, manager);
            if (entity instanceof SoftBlock || entity instanceof PowerUp)
                manager.destroyEntity(entity);
            if (entity instanceof PlayerEntity) {
                if (entity.getId() != parentPlayer.getEntity().getId())
                    parentPlayer.getEntity().addKill();
                manager.destroyEntity(entity);
            }
        }
        parentPlayer.currentBombs--;
        manager.destroyEntity(this);
    }

}
