package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {

    private final int id;
    private float x_screen, y_screen;
    private int x;

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }



    public Entity(int id) {
        this.id = id;
    }

    public float getX_screen() {
        return x_screen;
    }

    public float getY_screen() {
        return y_screen;
    }

    public int getId() {
        return id;
    }

    public void setX_screen(float x_screen) {
        this.x_screen = x_screen;
    }

    public void setY_screen(float y_screen) {
        this.y_screen = y_screen;
    }

    public  void draw(SpriteBatch batch, float temps){

    }
}
