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
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;
import fr.imt.boomeuuuuh.utils.Skin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player extends MovableEntity {

    private final static BitmapFont font = new BitmapFont();
    private String name;
    private boolean referred = false;
    private float fontWidth;
    private static final Map<String, Animation<TextureRegion>> skinTextures = new HashMap<>();

    static {
        for (int i = 1; i <= Skin.values().length; i++) {
            Texture up = new Texture("skin/skinUp" + i + ".png");
            Texture down = new Texture("skin/skinDown" + i + ".png");
            TextureRegion[] tabRegionDown = TextureRegion.split(down, 32, down.getHeight())[0];
            skinTextures.put("skin" + i + "Down", new Animation<TextureRegion>(0.25F, tabRegionDown));
            TextureRegion[] tabRegionUp = TextureRegion.split(up, 32, up.getHeight())[0];
            skinTextures.put("skin" + i + "Up", new Animation<TextureRegion>(0.25F, tabRegionUp));
        }
    }

    private Skin skin;

    private float animationTime = new Random().nextFloat();

    public Player(int id, Location location, World world) {
        super(id, location, world);
    }

    public boolean isReferred() {
        return referred;
    }

    public String getName() {
        return name;
    }

    public Skin getSkin() {
        return skin;
    }

    public void refer(String name, Skin skin) {
        this.referred = true;
        this.name = name;
        this.skin = skin;

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, name);
        fontWidth = glyphLayout.width;
    }

    @Override
    public short categoryBits() {
        return ENEMY_CATEGORY;
    }

    @Override
    public short maskBits() {
        return VOID_CATEGORY;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        super.draw(batch, delta);

        if (referred) {
            animationTime += delta;
            font.setColor(1f, (!isAffected ? 1f : 0), (!isAffected ? 1f : 0), 0.8f);
            font.draw(batch, name, getPixelX() + 16 - fontWidth / 2, getPixelY() + 45);
            batch.draw(skinTextures.get(skin.getDataName() + (getBody().getLinearVelocity().y > 0 ? "Up" : "Down")).getKeyFrame(animationTime, true), getPixelX(), getPixelY(), 32, 32);
        }
    }

    @Override
    public Shape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(14 / 100f);
        return shape;
    }
}
