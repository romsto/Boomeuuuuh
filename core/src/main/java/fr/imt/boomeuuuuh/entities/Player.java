package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class Player extends MovableEntity {

    private final static BitmapFont font = new BitmapFont();
    private String name;
    private String skin;
    private boolean referred = false;
    private float fontWidth;
    private static final Texture skin1 = new Texture("skin/skinDown1.png");

    public Player(int id, Location location, World world) {
        super(id, location, world);
    }

    public boolean isReferred() {
        return referred;
    }

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }

    public void refer(String name, String skin) {
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
            font.setColor(1f, (!isAffected ? 1f : 0), (!isAffected ? 1f : 0), 0.8f);
            font.draw(batch, name, getPixelX() + 16 - fontWidth / 2, getPixelY() + 45);
            batch.draw(skin1,getPixelX(),getPixelY(),32,32);
        }
    }

    @Override
    public Shape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(14 / 100f);
        return shape;
    }
}
