package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

import java.util.Random;

public class Player extends MovableEntity {

    private final static BitmapFont font = new BitmapFont();
    private String name;
    private String skin;
    private boolean referred = false;
    private float fontWidth;
    private static final Texture skin1Down = new Texture("skin/skinDown.png");
    private static final Texture skin1Up = new Texture("skin/skinUp.png");
    private static final Animation<TextureRegion> skin1AnimDown;
    private static final Animation<TextureRegion> skin1AnimUp;

    static {
        TextureRegion[] tabRegionDown = TextureRegion.split(skin1Down, 32, skin1Down.getHeight())[0];
        skin1AnimDown = new Animation<TextureRegion>(0.25F, tabRegionDown);
        TextureRegion[] tabRegionUp = TextureRegion.split(skin1Up, 32, skin1Up.getHeight())[0];
        skin1AnimUp = new Animation<TextureRegion>(0.25F, tabRegionUp);
    }

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
            animationTime += delta;
            font.setColor(1f, (!isAffected ? 1f : 0), (!isAffected ? 1f : 0), 0.8f);
            font.draw(batch, name, getPixelX() + 16 - fontWidth / 2, getPixelY() + 45);
            batch.draw(getBody().getLinearVelocity().y > 0 ? skin1AnimUp.getKeyFrame(animationTime, true) : skin1AnimDown.getKeyFrame(animationTime, true), getPixelX(), getPixelY(), 32, 32);
        }
    }

    @Override
    public Shape createShape() {
        CircleShape shape = new CircleShape();
        shape.setRadius(14 / 100f);
        return shape;
    }
}
