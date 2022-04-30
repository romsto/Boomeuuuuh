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

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.entities.*;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Bomb extends DynamicEntity {

    //Chara Variables
    private static final long delay = 3000; //ms
    //Explosion
    //private final ExplosionShape expShape;
    private final ClassicShape classicShape;

    //Time
    private final long endTime;
    public boolean calculating = false;
    private int power = 3;

    private final Player parentPlayer;

    public Bomb(int id, Player parentPlayer) {
        super(id);

        //SetTime
        endTime = System.currentTimeMillis() + delay;

        this.power = parentPlayer.getBombPower();

        //Create explosion shape
        //expShape = new ExplosionShape(power, power, power, power);
        classicShape = new ClassicShape(power);

        //SetPlayer
        this.parentPlayer = parentPlayer;
    }

    //-------------------------GET-------------------------
    public int getPower() {
        return power;
    }

    public PlayerEntity getPlayerEntity() {
        return parentPlayer.getEntity();
    }
    //-----------------------------------------------------

    public void checkExplosion(Collection<Entity> entityList, GameManager manager) {
        if (endTime < System.currentTimeMillis())
            explode(new ArrayList<>(entityList), manager);
    }

    public void forceExplode(Collection<Entity> entityList, GameManager manager) {
        Explode(entityList, manager);
    }

    /*private void Explode(Collection<Entity> entityList, GameManager manager){
        calculating = true;
        try {
            for (Entity e : expShape.calcExplosion(entityList, manager.getMapHeight(), manager.getMapWidth(), pos.getX(), pos.getY())) {
                if (e instanceof Bomb)
                    if (((Bomb) e).calculating)
                        ((Bomb) e).forceExplode(entityList, manager);
                if (e instanceof SoftBlock || e instanceof PowerUp)
                    manager.destroyEntity(e);
                if (e instanceof PlayerEntity) {
                    parentPlayer.getEntity().addKill((PlayerEntity) e); //Add kill to parent player
                    manager.destroyEntity(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        manager.destroyEntity(this);
        parentPlayer.currentBombs--;
    }*/

    private void Explode(Collection<Entity> entityList, GameManager manager) {
        List<Entity> explosion = classicShape.calcExplosion(entityList, this, manager.getMapHeight(), manager.getMapWidth(), pos.getX(), pos.getY());
        List<Entity> copied = new ArrayList<>(entityList);
        copied.remove(this);
        copied.removeAll(explosion.stream().filter(entity -> entity instanceof Bomb || entity instanceof PlayerEntity || entity instanceof PowerUp).collect(Collectors.toList()));
        for (Entity entity : explosion) {
            if (entity instanceof Bomb)
                ((Bomb) entity).forceExplode(copied, manager);
            if (entity instanceof SoftBlock || entity instanceof PowerUp)
                manager.destroyEntity(entity);
            if (entity instanceof PlayerEntity) {
                if (parentPlayer != null && parentPlayer.getEntity() != null && entity.getId() != parentPlayer.getEntity().getId())
                    parentPlayer.increaseGameKills();
                manager.destroyEntity(entity);
            }
        }
        if (parentPlayer != null)
            parentPlayer.currentBombs--;
        manager.destroyEntity(this);
    }

    public void explode(Collection<Entity> entityList, GameManager manager) {
        List<Entity> toDestroy = calculateExplosion(entityList, manager);
        Random random = new Random();
        for (Entity entity : toDestroy) {
            if (entity instanceof SoftBlock) {
                if (random.nextBoolean()) {
                    PowerUp power = new PowerUp(manager.getNewID());
                    power.setPos(entity.getPos());
                    manager.placeEntity(power);
                }
                manager.destroyEntity(entity);
            } else if (entity instanceof PlayerEntity) {
                if (parentPlayer != null && parentPlayer.getEntity() != null && entity.getId() != parentPlayer.getEntity().getId())
                    parentPlayer.increaseGameKills();
                manager.destroyEntity(entity);
            } else if (entity instanceof PowerUp || entity instanceof Bomb) {
                manager.destroyEntity(entity);
            }
        }
    }

    public List<Entity> calculateExplosion(Collection<Entity> toCare, GameManager manager) {
        toCare.remove(this);
        List<Entity> explosion = classicShape.calcExplosion(toCare, this, manager.getMapHeight(), manager.getMapWidth(), pos.getX(), pos.getY());
        for (Entity touched : new ArrayList<>(explosion)) {
            if (touched instanceof Bomb) {
                List<Entity> newExplode = ((Bomb) touched).calculateExplosion(toCare, manager);
                for (Entity entity : newExplode) {
                    if (!explosion.contains(entity))
                        explosion.add(entity);
                }
            }
        }
        explosion.add(this);
        if (parentPlayer != null)
            parentPlayer.currentBombs--;
        return explosion;
    }

}
