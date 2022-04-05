package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.players.Location;

public class Entity {
    //State Variables
    private final int id;
    private boolean isObstacle = false;

    //Dynamic Variables
    protected Location pos;

    public Entity(int id) {
        this.id = id;
    }

    //-------------------------GET-------------------------
    public boolean isObstacle() { return isObstacle; }
    public boolean isMovable() { return false; }

    public Location getPos(){ return pos; }
    public int getX(){ return pos.getX(); }

    public int getY() {
        return pos.getY();
    }

    public int getId() {
        return id;
    }

    //-----------------------------------------------------
    //------------------------SET--------------------------
    public void isObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public void setPos(Location pos) {
        this.pos = pos;
    }
    //-----------------------------------------------------
    //--------------------BASIC METHODS--------------------
    //-----------------------------------------------------


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
