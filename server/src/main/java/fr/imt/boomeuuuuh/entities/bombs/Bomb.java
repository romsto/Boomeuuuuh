package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.entities.*;
import fr.imt.boomeuuuuh.players.Player;

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
    //-----------------------------------------------------

    public void checkExplosion(List<Entity> entityList, int mapHeight, int mapWidth){
        if (!(endTime < System.currentTimeMillis()))
            Explode(entityList, mapHeight, mapWidth);
    }
    public void forceExplode(List<Entity> entityList, int mapHeight, int mapWidth){ Explode(entityList, mapHeight, mapWidth); }

    private void Explode(List<Entity> entityList, int mapHeight, int mapWidth){
        for (Entity e : expShape.calcExplosion(entityList, mapHeight, mapWidth, pos.getX(), pos.getY())){
            if (e instanceof Bomb)
                ((Bomb) e).forceExplode(entityList, mapHeight, mapWidth);
            if (e instanceof SoftBlock)
                ((SoftBlock) e).destroy();
            if (e instanceof PowerUp)
                ((PowerUp) e).destroy();
            if (e instanceof PlayerEntity)
                ((PlayerEntity) e).Kill();
        }
    }

}
