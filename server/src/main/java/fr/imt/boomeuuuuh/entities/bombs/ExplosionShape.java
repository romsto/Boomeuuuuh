package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.SoftBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ExplosionShape {
    //Explosion geometry
    private final int up;
    private final int down;
    private final int right;
    private final int left;

    public ExplosionShape(int up, int down, int right, int left){
        this.up = up; this.down = down; this.right = right; this.left = left;
    }

    public List<Entity> calcExplosion(List<Entity> entityList, int[][] baseMap, int bx, int by){
        List<Entity> ret = new ArrayList<>();

        //Calculate bounds
        int ly = by - down; int hy = by + up; int lx = bx - left; int hx = bx + right; //low y, high y ...

        //  Make sure explosion in map
        int height = baseMap.length; int width = baseMap[0].length;
        ly = Math.max(0, ly); hy = Math.min(height - 1, hy); lx = Math.max(0, lx); hx = Math.min(width - 1, hx);

        //  Look for walls (wall gets caught in explosion)
        for (int y = by - 1; y >= ly; y--)
            if (baseMap[y][bx] != 0) { ly = y; break; }
        for (int y = by + 1; y <= hy; y++)
            if (baseMap[y][bx] != 0) { hy = y; break; }

        for (int x = bx - 1; x >= lx; x--)
            if (baseMap[by][x] != 0) { lx = x; break; }
        for (int x = bx + 1; x <= hx; x++)
            if (baseMap[by][x] != 0) { hx = x; break; }

        ly -= 1; hy += 1; lx -= 1; hx += 1; //Adding one so that do we not have to <= but just <

        for (Entity e : entityList){
            int x = e.getX();
            int y = e.getY();
            if ((x == bx && lx < x && x < hx) || (y == by && ly < y && y < hy))
                ret.add(e);
        }

        return ret;
    }
}
