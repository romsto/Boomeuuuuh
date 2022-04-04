package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class Bomb extends MovableEntity {

    protected int power = 1;

    public Bomb(int id, Location location, World world, int power) {
        super(id, location, world);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
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
    public Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14 / 100f, 14 / 100f);
        return shape;
    }
}
