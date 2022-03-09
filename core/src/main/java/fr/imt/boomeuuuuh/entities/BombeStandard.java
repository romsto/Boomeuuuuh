package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BombeStandard extends Bomb {
    Texture sheetTexture = new Texture("m_merged.png");
    TextureRegion[][] tmp = TextureRegion.split(sheetTexture, 32, sheetTexture.getHeight());
    TextureRegion[] tabRegion = new TextureRegion[2];
    int index = 0;
    Animation img = new Animation(1, tabRegion);
    private TextureRegion regionCourante;



    public BombeStandard(int id) {
        super(id);


    }

    public void draw(SpriteBatch batch, float temps) {
        for (int j = 0; j < 2; j++) {
            tabRegion[index++] = tmp[0][j];}
        regionCourante = (TextureRegion) img.getKeyFrame(temps, true);
        regionCourante.setRegion(regionCourante, 0, 0, 32, 32);
        batch.draw(regionCourante,this.getX(),this.getY());



    }

    }



