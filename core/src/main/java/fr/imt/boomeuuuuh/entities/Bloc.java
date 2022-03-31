package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class Bloc extends Entity {

    public Bloc(int id, Location location, World world) {
        super(id, location, world, BodyDef.BodyType.StaticBody);
    }

    @Override
    public short categoryBits() {
        return Entity.SOLID_CATEGORY;
    }

    @Override
    public short maskBits() {
        return Entity.PLAYER_CATEGORY;
    }
}
