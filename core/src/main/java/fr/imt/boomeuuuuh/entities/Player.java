package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class Player extends MovableEntity {

    private boolean reffered = false;
    private String name;
    private String skin;

    public Player(int id, Location location, World world) {
        super(id, location, world);
    }

    public boolean isReffered() {
        return reffered;
    }

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }

    public void reffer(String name, String skin) {
        this.reffered = true;
        this.name = name;
        this.skin = skin;
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
