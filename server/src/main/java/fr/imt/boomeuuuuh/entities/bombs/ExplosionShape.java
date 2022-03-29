package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.HardBlock;
import fr.imt.boomeuuuuh.entities.SoftBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExplosionShape {
    //Explosion geometry
    private final int up;
    private final int down;
    private final int right;
    private final int left;

    public ExplosionShape(int up, int down, int right, int left){
        this.up = up; this.down = down; this.right = right; this.left = left;
    }

    public List<Entity> calcExplosion(Collection<Entity> entityList, int mapHeight, int mapWidth, int bx, int by){
        //  Make sure explosion in map
        int[] bounds = {Math.min(mapHeight - 1, by + up),
                Math.min(mapWidth - 1, bx + right),
                Math.max(0, by - down),
                Math.max(0, bx - left)}; //up, right, down, left

        for (Entity e : entityList){
            int x = e.getX();
            int y = e.getY();

            if(e instanceof SoftBlock || e instanceof HardBlock)
            if((bounds[3] <= x || x <= bounds[1]) && y == by){
                if(x < bx){ bounds[3] = x; }
                else if (x > bx) {bounds[1] = x;}
                else{ bounds[3] = bx; bounds[1] = bx; }
            } else if ((bounds[2] <= y || y <= bounds[0]) && x == bx) {
                if(y < by){ bounds[2] = y; }
                else if (y > by) {bounds[0] = y;}
                else{ bounds[2] = by; bounds[0] = by; }
            }
        }

        List<Entity> ret = new ArrayList<>();

        for (Entity e : entityList){
            int x = e.getX();
            int y = e.getY();

            if(((bounds[3] <= x || x <= bounds[1]) && y == by) || ((bounds[2] <= y || y <= bounds[0]) && x == bx)){
                ret.add(e);
            }
        }

        return ret;
    }
}
