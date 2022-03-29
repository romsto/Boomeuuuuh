package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.entities.*;
import fr.imt.boomeuuuuh.players.Player;

import java.util.Collection;
import java.util.List;

public class Bomb extends DynamicEntity {

    //Chara Variables
    private final long delay = 3000; //ms
    private final int power = 3;

    //Time
    private final long endTime;

    //Explosion
    private final ExplosionShape expShape;

    private final Player parentPlayer;

    public Bomb(int id, Player parentPlayer) {
        super(id);

        //SetTime
        endTime = System.currentTimeMillis() + delay;

        //Create explosion shape
        expShape = new ExplosionShape(power, power, power, power);

        //SetPlayer
        this.parentPlayer = parentPlayer;
    }

    //-------------------------GET-------------------------
    public int getPower() { return power; }
    public PlayerEntity getPlayerEntity(){ return parentPlayer.getEntity(); }
    //-----------------------------------------------------

    public void checkExplosion(Collection<Entity> entityList, GameManager manager){
        if (!(endTime < System.currentTimeMillis()))
            Explode(entityList, manager);
    }
    public void forceExplode(Collection<Entity> entityList, GameManager manager){ Explode(entityList, manager); }

    private void Explode(Collection<Entity> entityList, GameManager manager){
        for (Entity e : expShape.calcExplosion(entityList, manager.getMapHeight(), manager.getMapWidth(), pos.getX(), pos.getY())){
            if (e instanceof Bomb)
                ((Bomb) e).forceExplode(entityList, manager);
            if (e instanceof SoftBlock || e instanceof PowerUp)
                manager.destroyEntity(e);
            if (e instanceof PlayerEntity)
                ((PlayerEntity) e).Kill(manager, this);
        }
    }

}
