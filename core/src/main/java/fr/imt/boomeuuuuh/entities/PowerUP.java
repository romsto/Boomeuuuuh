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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class PowerUP extends Entity {

    private final static Texture texture = new Texture("powerup/powerup.png");

    public PowerUP(int id, Location location, World world) {
        super(id, location);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.draw(texture, getPixelX(), getPixelY(), 32, 32);
    }

    @Override
    public short categoryBits() {
        return 0;
    }

    @Override
    public short maskBits() {
        return 0;
    }

    @Override
    public Shape createShape() {
        return null;
    }
}
