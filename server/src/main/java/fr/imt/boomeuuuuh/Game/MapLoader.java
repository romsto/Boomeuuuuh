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

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.HardBlock;
import fr.imt.boomeuuuuh.entities.PowerUp;
import fr.imt.boomeuuuuh.entities.SoftBlock;
import fr.imt.boomeuuuuh.players.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

public class MapLoader {
    //Map format -> Only represents entities
    //  - Entity number represents it
    // eg . 0,50,50,0,0,50,...
    //      0,50,0,0,0,...
    //      ...
    public Map LoadMap(String mapName, GameManager man) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Maps/" + mapName + ".txt");
        if (inputStream == null)
            return null;
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bf = new BufferedReader(reader);

        Collection<Entity> E = new ArrayList<>();
        ArrayList<Location> spawn = new ArrayList<>();
        String st;
        int y = 0;
        int x = 0;
        while (true) {
            try {
                st = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (st == null)
                break;

            String[] s = st.split(",");
            x = 0;
            for (String p : s) {
                Entity e = null;
                int i = 0;
                try {
                    i = Integer.parseInt(p);
                }catch (Exception exception){ Boomeuuuuh.logger.severe(exception.toString());}

                switch (i) {
                    case 50 -> e = new SoftBlock(man.getNewID());
                    case 40 -> e = new HardBlock(man.getNewID());
                    case 20 -> e = new PowerUp(man.getNewID());
                    case 4 -> spawn.add(new Location(x, y));
                    default -> {
                    }
                }
                if (e != null) {
                    e.setPos(new Location(x, y));
                    E.add(e);
                }
                x += 1;
            }
            y += 1;
        }

        return new Map(x + 1, y + 1, E, spawn);
    }
}
