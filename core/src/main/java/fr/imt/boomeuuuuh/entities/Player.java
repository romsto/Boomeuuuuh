package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class Player extends MovableEntity {

    public Player(int id, Location location, World world) {
        super(id, location, world);
    }

    @Override
    public short categoryBits() {
        return PLAYER_CATEGORY;
    }

    @Override
    public short maskBits() {
        return SOLID_CATEGORY;
    }
}
