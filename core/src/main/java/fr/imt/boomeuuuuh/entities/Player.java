package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class Player extends MovableEntity {

    private final BitmapFont font = new BitmapFont();
    private String name;
    private String skin;
    private boolean referred = false;
    private float fontWidth;

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
        return PLAYER_CATEGORY;
    }

    @Override
    public short maskBits() {
        return SOLID_CATEGORY;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        super.draw(batch, delta);

        if (referred) {
            font.setColor(1f, 1f, 1f, 0.8f);
            font.draw(batch, name, getPixelX() + 16 - fontWidth / 2, getPixelY() + 45);
        }
    }

    @Override
    public Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14 / 100f, 14 / 100f);
        return shape;
    }
}
