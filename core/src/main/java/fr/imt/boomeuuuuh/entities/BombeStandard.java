package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.imt.boomeuuuuh.Game;


public class BombeStandard extends Bomb {
    Texture sheetTexture = new Texture("Bomb/Clignx16.png"); // Image avec toutes les frames
    Texture explosion_verti = new Texture("Bomb/fire_verti.png");
    Texture explosion_horiz = new Texture("Bomb/fire_horiz.png");
    TextureRegion[][] tmp = TextureRegion.split(sheetTexture, 32, sheetTexture.getHeight()); // divise sheetTexture selon la taille de nos frames
    TextureRegion[] tabRegion = new TextureRegion[32]; // Tableau contenant nos frames
    int index = 0;
    private boolean Clign = true;
    private boolean feu = false;
    private long t;
    Animation img = new Animation(0.25F, tabRegion);//Obj Animation qui contient le tableau de frame et le temps de chaque frame
    private TextureRegion regionCourante; // la frame actuelle, qui est une région de la sheet contenant toutes les frames
    int right;
    int left;
    int up;
    int down ;

    public int getUp() {
        return up;
    }
    public void setUp(int up) {
        this.up = up;
    }
    public int getDown() {
        return down;
    }
    public void setDown(int down) {
        this.down = down;
    }
    public int getRight() {
        return right;
    }
    public void setRight(int right) {
        this.right = right;
    }
    public int getLeft() {
        return left;
    }
    public void setLeft(int left) {
        this.left = left;
    }



    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public boolean isClign() {
        return Clign;
    }

    public void setClign(boolean clign) {
        Clign = clign;
    }

    public boolean isFeu() {
        return feu;
    }

    public void setFeu(boolean feu) {
        this.feu = feu;
    }


    public BombeStandard(int id, int xc, int yc,int pow) {
        super(id);
        this.setX(xc);this.setY(yc);
        this.setPower(pow);
        this.setT(System.nanoTime());
        this.setUp(this.getY()+32*power);
        this.setDown(this.getY()-32*power);
        this.setLeft(this.getX()-32*power);
        this.setRight(this.getX()+32*power);
        for (int j = 0; j < 32; j++) {
            tabRegion[index++] = tmp[0][j];
        } // on parcours les regions
    }

    public void draw(SpriteBatch batch, float temps) {
        if (isClign()) {

            regionCourante = (TextureRegion) img.getKeyFrame(temps, false); // On choisi la region correspondant au temps t
            regionCourante.setRegion(regionCourante,0, 0, 32, 32);// on set
            batch.draw(regionCourante, this.getX(), this.getY()); // on dessine cette image , aux coordonnées
            if (System.nanoTime() - this.getT() > 3e9) {
                this.setFeu(true);
                this.setClign(false);
            }
        }
        if (isFeu()) {
            for (Entity E : Game.getInstance().getEntities()) {
                if (E instanceof Bloc) {
                    if (E.getY() == this.getY()) {
                        if ((this.getX() - power <= E.getX()) & (E.getX() <= this.getX() - 1)) {
                            this.setLeft( Math.max(this.getLeft(), E.getX()));
                        }
                        if ((this.getX() + power >= E.getX()) & (E.getX() >= this.getX() + 1)) {
                            this.setRight(Math.min(this.getRight(), E.getX()));
                        }
                    }
                    if (E.getX() == this.getX()) {
                        if ((this.getY() - power <= E.getY()) & (E.getY() <= this.getY() - 1)) {
                            this.setDown(Math.max(this.getDown(), E.getY()));
                        }
                        if ((this.getY() + power >= E.getY()) & (E.getY() >= this.getY() + 1)) {
                            this.setUp(Math.min(this.getUp(), E.getY()));
                        }
                    }
                }

            }

            for (int k = 0; k < this.power; k++) {
                if (this.getX() + k <= Math.min(this.getX() + power, this.getRight())) {
                    batch.draw(explosion_horiz, this.getX() + (32 * k), this.getY());
                }
                if (this.getX() - k >= Math.max(this.getX() - power, this.getLeft())) {
                    batch.draw(explosion_horiz, this.getX() - (32 * k), this.getY());
                }
                if (this.getY() + k <= Math.min(this.getY() + power,this.getUp())) {
                    batch.draw(explosion_verti, this.getX(), this.getY() + (32 * k));
                }
                if (this.getY() - k >= Math.max(this.getY() - power,this.getDown())) {
                    batch.draw(explosion_verti, this.getX(), this.getY() - (32 * k));
                }
            }
            for (Entity B : Game.getInstance().getEntities()) {
                if (B instanceof BombeStandard) {

                    ;
                    if (((left <= B.getX()) & (B.getX() <= right) & (B.getY() == this.getY())) ||
                            ((down <= B.getY()) & (B.getY() <= up) & (B.getX() == this.getX()))) {
                        ((BombeStandard) B).setClign(false);
                        ((BombeStandard) B).setFeu(true);
                    }
                }
            }
            if (System.nanoTime() - this.getT() > 35e8) {
                feu = false;
            }
        }


    }


}







