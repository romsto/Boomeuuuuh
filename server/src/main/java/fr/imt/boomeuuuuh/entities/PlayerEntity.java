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

package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Random;

public class PlayerEntity extends DynamicEntity {

    private final Player playerRep;
    private static final Random random = new Random();

    public PlayerEntity(Player playerRepresented, int id) {
        super(id);

        playerRep = playerRepresented;
    }

    public Player getPlayer() {
        return playerRep;
    }

    @Override
    public void setPos(Location pos) {
        super.setPos(pos);

        GameManager manager = playerRep.getLobby().getGameManager();
        if (manager == null)
            return;
        for (Entity entity : new ArrayList<>(manager.getEntityList())) {
            if (!(entity instanceof PowerUp))
                continue;
            if (!entity.getPos().equals(pos))
                continue;

            int rd = random.nextInt(10);
            if (rd <= 1)
                playerRep.increasePower();
            else if (rd <= 7)
                playerRep.increaseMaxBombs();
            else
                playerRep.increaseSpeed();

            manager.destroyEntity(entity);
            break;
        }
    }
}
