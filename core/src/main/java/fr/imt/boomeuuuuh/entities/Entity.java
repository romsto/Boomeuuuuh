package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import fr.imt.boomeuuuuh.utils.Location;

public abstract class Entity {

    public static short PLAYER_CATEGORY = 0x0001;
    public static short SOLID_CATEGORY = 0x0002;

    private final int id;
    private final Body body;

    public Entity(int id, Location location, World world, BodyDef.BodyType bodyType) {
        this.id = id;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.x = (location.getX() * 32 + 16) / 100f;
        bodyDef.position.y = (location.getY() * 32 + 16) / 100f;

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16f / 100f, 16f / 100f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = categoryBits();
        fixtureDef.filter.maskBits = maskBits();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public float getPixelX() {
        return (body.getPosition().x * 100) - 16;
    }

    public float getPixelY() {
        return (body.getPosition().y * 100) - 16;
    }

    public int getBlocX() {
        return (int) (getPixelX() / 32);
    }

    public int getBlocY() {
        return (int) (getPixelY() / 32);
    }

    public int getId() {
        return id;
    }

    public Body getBody() {
        return body;
    }

    public void draw(SpriteBatch batch, float delta) {
    }

    public void dispose() {
    }

    public abstract short categoryBits();

    public abstract short maskBits();
}
