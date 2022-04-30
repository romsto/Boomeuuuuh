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
import com.badlogic.gdx.physics.box2d.*;
import fr.imt.boomeuuuuh.utils.Location;

public abstract class Entity {

    public static short PLAYER_CATEGORY = 0x0001;
    public static short SOLID_CATEGORY = 0x0002;
    public static short BOMB_CATEGORY = 0x0003;
    public static short ENEMY_CATEGORY = 0x0004;
    public static short VOID_CATEGORY = 0x0005;

    private final int id;
    private Body body;

    private Location createLocation;

    /**
     * Constructs an Entity
     *
     * @param id       id
     * @param location of spawn
     * @param world    of spawn
     * @param bodyType type of body to create
     */
    public Entity(int id, Location location, World world, BodyDef.BodyType bodyType) {
        this.id = id;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.fixedRotation = true;
        bodyDef.position.x = (location.getX() * 32 + 16) / 100f;
        bodyDef.position.y = (location.getY() * 32 + 16) / 100f;

        body = world.createBody(bodyDef);
        Shape shape = createShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = categoryBits();
        fixtureDef.filter.maskBits = maskBits();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    /**
     * Constructs a non-body entity
     *
     * @param id       id
     * @param location of spawn
     */
    public Entity(int id, Location location) {
        this.id = id;
        this.createLocation = location;
    }

    /**
     * Get the X position in pixel unit
     *
     * @return x in pixel unit
     */
    public float getPixelX() {
        if (body == null)
            return createLocation.getX() * 32;
        return (body.getPosition().x * 100) - 16;
    }

    /**
     * Get the Y position in pixel unit
     *
     * @return y in pixel unit
     */
    public float getPixelY() {
        if (body == null)
            return createLocation.getY() * 32;
        return (body.getPosition().y * 100) - 16;
    }

    /**
     * Get bloc X
     *
     * @return bloc X
     */
    public int getBlocX() {
        if (body == null)
            return createLocation.getX();
        return (int) ((getPixelX() + 16) / 32);
    }

    /**
     * Get Bloc Y
     *
     * @return bloc y
     */
    public int getBlocY() {
        if (body == null)
            return createLocation.getY();
        return (int) ((getPixelY() + 16) / 32);
    }

    /**
     * Get the unique ID of the entity
     *
     * @return entity id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the Box2D body of the entity
     *
     * @return box2D body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Move the entity to a specific Location (bloc location)
     *
     * @param location bloc
     */
    public void teleport(Location location) {
        body.setTransform(new Vector2((location.getX() * 32 + 16) / 100f, (location.getY() * 32 + 16) / 100f), 0);
    }

    public void draw(SpriteBatch batch, float delta) {
    }

    public void dispose() {
    }

    public abstract short categoryBits();

    public abstract short maskBits();

    /**
     * Called when creating the body of the entity
     *
     * @return Shape of the entity
     */
    public Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16f / 100f, 16f / 100f);
        return shape;
    }
}
