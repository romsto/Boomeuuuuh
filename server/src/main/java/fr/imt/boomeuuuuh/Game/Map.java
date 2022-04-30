/*
 * Copyright (c) 2022.
 * Authors : Storaï R, Faure B, Mathieu A, Garry A, Nicolau T, Bregier M.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
