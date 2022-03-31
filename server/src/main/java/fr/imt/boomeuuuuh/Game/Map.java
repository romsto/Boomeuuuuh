package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.players.Location;

import java.util.ArrayList;
import java.util.Collection;

public class Map {

    public final int Width;
    public final int Height;
    public final Collection<Entity> mapEntities;
    final ArrayList<Location> spawns;
    int curSpawn = 0;

    public Map(int width, int height, Collection<Entity> entities, ArrayList<Location> spawns){
        Width = width;
        Height = height;
        mapEntities = entities;
        this.spawns = spawns;
    }

    public Location nextSpawn(){
        Location l = spawns.get(curSpawn);
        curSpawn = (curSpawn + 1) % spawns.size();
        return l;
    }
}
