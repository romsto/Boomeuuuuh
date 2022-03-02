package fr.imt.boomeuuuuh.entities;

public class Entity {

    private final int id;
    private float x,y;

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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public  void draw(){

    }
}
