package fr.imt.boomeuuuuh.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity {

    protected final int id;
    protected float x, y;
    protected boolean visible;
    protected Sprite sprite;

    public Entity(int id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Sprite getSprite() {return sprite;}

    public void setSprite(Sprite sprite) {this.sprite = sprite;}
}
