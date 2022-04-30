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

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.utils.Location;

public class Bomb extends Entity {

    protected int power = 1;

    public Bomb(int id, Location location, World world, int power) {
        super(id, location, world, BodyDef.BodyType.StaticBody);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public short categoryBits() {
        if (Game.getInstance() == null || Game.getInstance().player == null)
            return SOLID_CATEGORY;

        boolean inPlayer = getBlocX() == Game.getInstance().player.getBlocX() && getBlocY() == Game.getInstance().player.getBlocY();
        if (inPlayer)
            Game.getInstance().toChangeCollision.add(this);
        return inPlayer ? BOMB_CATEGORY : SOLID_CATEGORY;
    }

    @Override
    public short maskBits() {
        if (Game.getInstance() == null || Game.getInstance().player == null)
            return PLAYER_CATEGORY;

        boolean inPlayer = getBlocX() == Game.getInstance().player.getBlocX() && getBlocY() == Game.getInstance().player.getBlocY();
        return inPlayer ? SOLID_CATEGORY : PLAYER_CATEGORY;
    }

    @Override
    public Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14 / 100f, 14 / 100f);
        return shape;
    }
}
