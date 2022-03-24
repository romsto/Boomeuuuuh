package fr.imt.boomeuuuuh.entities;

public class Entity {
    //State Variables
    private final int id;
    private boolean isObstacle = false;

    //Dynamic Variables
    protected int x,y;

    public Entity(int id) {
        this.id = id;
    }

    //-------------------------GET-------------------------
    public boolean isObstacle() { return isObstacle; }
    public boolean isMovable() { return false; }

    public int getX(){ return x; }
    public int getY(){ return y; }

    public int getId() { return id; }
    //-----------------------------------------------------
    //------------------------SET--------------------------
    public void isObstacle(boolean isObstacle){ this.isObstacle = isObstacle; }
    //-----------------------------------------------------
}
