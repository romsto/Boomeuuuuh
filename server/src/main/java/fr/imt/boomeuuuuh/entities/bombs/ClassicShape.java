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

package fr.imt.boomeuuuuh.entities.bombs;

import fr.imt.boomeuuuuh.entities.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassicShape {

    private final int power;

    public ClassicShape(int power) {
        this.power = power;
    }

    public List<Entity> calcExplosion(Collection<Entity> entityList, Entity entity, int mapHeight, int mapWidth, int bx, int by) {
        int up = Math.min(mapHeight - 1, by + power);
        int down = Math.max(1, by - power);
        int left = Math.max(1, bx - power);
        int right = Math.min(mapWidth - 1, bx + power);

        for (Entity E : entityList) {
            if (!(E instanceof SoftBlock || E instanceof HardBlock)) continue;
            boolean hard = E instanceof HardBlock;
            if (E.getY() == by) {
                if ((bx - power <= E.getX()) & (E.getX() <= bx - 1)) {
                    left = Math.max(left, E.getX());
                }
                if ((bx + power >= E.getX()) & (E.getX() >= bx + 1)) {
                    right = Math.min(right, E.getX());
                }
            }

            if (E.getX() == bx) {
                if ((by - power <= E.getY()) & (E.getY() <= by - 1)) {
                    down = Math.max(down, E.getY());
                }
                if ((by + power >= E.getY()) & (E.getY() >= by + 1)) {
                    up = Math.min(up, E.getY());
                }
            }
        }

        List<Entity> entities = new ArrayList<>();

        for (Entity B : entityList) {
            if (B == entity)
                continue;
            if (B instanceof Bomb) {
                if (((Bomb) B).calculating) continue;
                if (((left <= B.getX()) && (B.getX() <= right) && (B.getY() == by)) || ((down <= B.getY()) && (B.getY() <= up) && (B.getX() == bx)))
                    entities.add(B);
            } else if (B instanceof SoftBlock) {
                if (((left == B.getX() || right == B.getX()) && B.getY() == by) || ((up == B.getY() || down == B.getY()) && B.getX() == bx))
                    entities.add(B);
            } else if (B instanceof PlayerEntity || B instanceof PowerUp) {
                if (((left <= B.getX()) && (B.getX() <= right) && (B.getY() == by)) || ((down <= B.getY()) && (B.getY() <= up) && (B.getX() == bx)))
                    entities.add(B);
            }
        }

        return entities;
    }
}
