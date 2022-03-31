package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class HardBlock extends Bloc {

    public HardBlock(int id, Location location, World world) {
        super(id, location, world);
    }

    @Override
    public void draw(SpriteBatch batch, float temps) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
