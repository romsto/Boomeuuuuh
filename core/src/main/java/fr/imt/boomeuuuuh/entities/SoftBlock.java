package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.utils.Location;

public class SoftBlock extends Bloc {

    private final static Texture texture = new Texture("MapImg/bale.png");

    public SoftBlock(int id, Location location, World world) {
        super(id, location, world);
    }

    @Override
    public void draw(SpriteBatch batch, float temps) {
        batch.draw(texture, getPixelX(), getPixelY());
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
