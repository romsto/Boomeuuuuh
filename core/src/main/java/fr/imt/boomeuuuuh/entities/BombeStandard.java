package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BombeStandard extends Bomb {
    Texture sheetTexture = new Texture("Bomb/Clignx16.png"); // Image avec toutes les frames
    Texture explosion_verti = new Texture("Bomb/fire_verti.png");
    Texture explosion_horiz = new Texture("Bomb/fire_horiz.png");
    TextureRegion[][] tmp = TextureRegion.split(sheetTexture, 32, sheetTexture.getHeight()); // divise sheetTexture selon la taille de nos frames
    TextureRegion[] tabRegion = new TextureRegion[32]; // Tableau contenant nos frames
    int index = 0;
    boolean explode = false;
    long t;
    Animation img = new Animation(0.25F, tabRegion);//Obj Animation qui contient le tableau de frame et le temps de chaque frame
    private TextureRegion regionCourante; // la frame actuelle, qui est une région de la sheet contenant toutes les frames


    public BombeStandard(int id) {
        super(id);
        t = System.nanoTime();
        for (int j = 0; j < 32; j++) {
            tabRegion[index++] = tmp[0][j];
        } // on parcours les regions
    }

    public void draw(SpriteBatch batch, float temps) {
        if (!explode) {
            regionCourante = (TextureRegion) img.getKeyFrame(temps, false); // On choisi la region correspondant au temps t
            regionCourante.setRegion(regionCourante, 0, 0, 32, 32);// on set
            batch.draw(regionCourante, this.getX(), this.getY()); // on dessine cette image , aux coordonnées

            if (System.nanoTime() - t > 3e9) {
                explode = true;
                for (int k = 0; k < this.power; k++) { //mettre les if pour verifier si des objets sont sur le chemin
                    batch.draw(explosion_horiz, this.getX() + (32 * k), this.getY());
                    batch.draw(explosion_horiz, this.getX() - (32 * k), this.getY());
                    batch.draw(explosion_verti, this.getX(), this.getY() + (32 * k));
                    batch.draw(explosion_verti, this.getX(), this.getY() - (32 * k));

                }


            }

        }
    }
}





