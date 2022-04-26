package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.players.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Map {

    public final int Width;
    public final int Height;
    public final Collection<Entity> mapEntities;
    final ArrayList<Location> spawns;
    final ArrayList<Integer> takenSpawns;
    int curSpawn = 0;
    private final Random random = new Random();

    public Map(int width, int height, Collection<Entity> entities, ArrayList<Location> spawns) {
        Width = width;
        Height = height;
        mapEntities = entities;
        this.spawns = spawns;
        this.takenSpawns = new ArrayList<>();
    }

    /**
     * Finds the next available spawn
     *
     * @return spawn
     */
    public Location nextSpawn() {
        int sp = random.nextInt(spawns.size());
        while (takenSpawns.contains(sp))
            sp = random.nextInt(spawns.size());
        takenSpawns.add(sp);
        return spawns.get(sp);
    }
}
