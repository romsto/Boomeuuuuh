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

    public MovableEntity(int id, Location location, World world) {
        super(id, location, world, BodyDef.BodyType.DynamicBody);

        this.toBlocX = location.getX();
        this.toBlocY = location.getY();
    }

    public int getToBlocX() {
        return toBlocX;
    }

    public void setToBlocX(int toBlocX) {
        this.toBlocX = toBlocX;
        //this.elapsed = 0;
    }

    public int getToBlocY() {
        return toBlocY;
    }

    public void setToBlocY(int toBlocY) {
        this.toBlocY = toBlocY;
        //this.elapsed = 0;
    }

    public void setToBloc(Location location) {
        if (location.getX() == toBlocX && location.getY() == toBlocY)
            return;
        setToBlocX(location.getX());
        setToBlocY(location.getY());
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
                getBody().setLinearVelocity(new Vector2((toBlocX * 32 - getPixelX()), (toBlocY * 32 - getPixelY())).nor());
        } else {
            elapsed = 0;
            getBody().setLinearVelocity(new Vector2());
        }
    }
}
