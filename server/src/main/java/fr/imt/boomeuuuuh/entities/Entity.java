package fr.imt.boomeuuuuh.entities;

public class Entity {
    //State Variables
    private final int id;
    private boolean isObstacle = false;

    //Dynamic Variables
    private float x,y;

    public Entity(int id) {
        this.id = id;
    }

    //-------------------------GET-------------------------
    public boolean isObstacle() { return isObstacle; }
    public boolean isMovable() { return false; }

    public int getId() { return id; }
    //-----------------------------------------------------
    //------------------------SET--------------------------
    public void isObstacle(boolean isObstacle){ this.isObstacle = isObstacle; }
    //-----------------------------------------------------
}
