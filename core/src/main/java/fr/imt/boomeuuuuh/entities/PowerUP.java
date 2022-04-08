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
