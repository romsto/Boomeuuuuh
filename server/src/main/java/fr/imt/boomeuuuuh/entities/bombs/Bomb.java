package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.entities.DynamicEntity;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.SoftBlock;

import java.util.List;

public class Bomb extends DynamicEntity {

    //Chara Variables
    private final long delay = 3000; //ms
    private final int power = 3;

    //Time
    private final long endTime;

    //Explosion
    private final ExplosionShape expShape;

    public Bomb(int id) {
        super(id);

        //SetTime
        endTime = System.currentTimeMillis() + delay;

        //Create explosion shape
        expShape = new ExplosionShape(power, power, power, power);
    }

    public void checkExplosion(List<Entity> entityList, int[][] baseMap){
        if (!(endTime < System.currentTimeMillis()))
            Explode(entityList, baseMap);
    }
    public void forceExplode(List<Entity> entityList, int[][] baseMap){ Explode(entityList, baseMap); }

    private void Explode(List<Entity> entityList, int[][] baseMap){
        for (Entity e : expShape.calcExplosion(entityList, baseMap, x, y)){
            //TODO : Do all cases
            if (e instanceof Bomb)
                ((Bomb) e).forceExplode(entityList, baseMap);
            if (e instanceof SoftBlock)
                ((SoftBlock) e).destroy();
        }
    }

}
