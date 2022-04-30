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


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public abstract class MovableEntity extends Entity {

    public boolean isAffected = true;
    private int toBlocX, toBlocY;
    private double elapsed = 0;
    private int speed = 1;

    public MovableEntity(int id, Location location, World world) {
        super(id, location, world, BodyDef.BodyType.DynamicBody);

        this.toBlocX = location.getX();
        this.toBlocY = location.getY();
    }

    public int getToBlocX() {
        return toBlocX;
    }

    private void setToBlocX(int toBlocX) {
        this.toBlocX = toBlocX;
        //this.elapsed = 0;
    }

    public int getToBlocY() {
        return toBlocY;
    }

    private void setToBlocY(int toBlocY) {
        this.toBlocY = toBlocY;
        //this.elapsed = 0;
    }

    public void setToBloc(Location location, int speed) {
        if (location.getX() == toBlocX && location.getY() == toBlocY)
            return;
        setToBlocX(location.getX());
        setToBlocY(location.getY());
        this.speed = speed;
    }

    public boolean alreadyInDestination() {
        return Math.abs(new Vector2(toBlocX * 32 - getPixelX(), toBlocY * 32 - getPixelY()).len2()) <= 5;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        if (!isAffected)
            return;

        if (!alreadyInDestination()) {
            elapsed += delta;
            if (elapsed >= 2) {
                teleport(new Location(getToBlocX(), getToBlocY()));
                getBody().setLinearVelocity(new Vector2());
                elapsed = 0;
            } else
                getBody().setLinearVelocity(new Vector2((toBlocX * 32 - getPixelX()), (toBlocY * 32 - getPixelY())).nor().scl(1 + (speed - 1) / 6f));
        } else {
            elapsed = 0;
            getBody().setLinearVelocity(new Vector2());
        }
    }
}
