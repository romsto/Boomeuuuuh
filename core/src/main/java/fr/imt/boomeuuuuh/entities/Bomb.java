package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.utils.Location;

public class Bomb extends Entity {

    protected int power = 1;

    public Bomb(int id, Location location, World world, int power) {
        super(id, location, world, BodyDef.BodyType.StaticBody);
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
        if (Game.getInstance() == null || Game.getInstance().player == null)
            return SOLID_CATEGORY;

        boolean inPlayer = getBlocX() == Game.getInstance().player.getBlocX() && getBlocY() == Game.getInstance().player.getBlocY();
        if (inPlayer)
            Game.getInstance().toChangeCollision.add(this);
        return inPlayer ? BOMB_CATEGORY : SOLID_CATEGORY;
    }

    @Override
    public short maskBits() {
        boolean inPlayer = getBlocX() == Game.getInstance().player.getBlocX() && getBlocY() == Game.getInstance().player.getBlocY();
        return inPlayer ? SOLID_CATEGORY : PLAYER_CATEGORY;
    }

    @Override
    public Shape createShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14 / 100f, 14 / 100f);
        return shape;
    }
}
