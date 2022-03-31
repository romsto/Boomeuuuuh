package fr.imt.boomeuuuuh.entities;


import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public abstract class MovableEntity extends Entity {

    public MovableEntity(int id, Location location, World world) {
        super(id, location, world, BodyDef.BodyType.DynamicBody);
    }

}
