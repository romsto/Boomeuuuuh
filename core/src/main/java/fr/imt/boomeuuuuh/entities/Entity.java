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

    public Entity(int id, Location location) {
        this.id = id;
        this.createLocation = location;
    }

    public float getPixelX() {
        if (body == null)
            return createLocation.getX() * 32;
        return (body.getPosition().x * 100) - 16;
    }

    public float getPixelY() {
        if (body == null)
            return createLocation.getY() * 32;
        return (body.getPosition().y * 100) - 16;
    }

    public int getBlocX() {
        if (body == null)
            return createLocation.getX();
        return (int) ((getPixelX() + 16) / 32);
    }

    public int getBlocY() {
        if (body == null)
            return createLocation.getY();
        return (int) ((getPixelY() + 16) / 32);
    }

    public int getId() {
        return id;
    }

    public Body getBody() {
        return body;
    }

    public void teleport(Location location) {
        body.setTransform(new Vector2((location.getX() * 32 + 16) / 100f, (location.getY() * 32 + 16) / 100f), 0);
    }

    public void draw(SpriteBatch batch, float delta) {
    }

    public void dispose() {
    }

    public abstract short categoryBits();

    public abstract short maskBits();

    public Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16f / 100f, 16f / 100f);
        return shape;
    }
}
