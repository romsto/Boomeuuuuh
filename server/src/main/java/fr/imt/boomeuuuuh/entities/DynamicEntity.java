package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.players.Location;

public class DynamicEntity extends Entity {

    public boolean hasMovedSinceLastTick = false;

    public DynamicEntity(int id) {
        super(id);
    }

    //-------------------------GET-------------------------
    @Override
    public boolean isMovable() {
        return true;
    }
    //-----------------------------------------------------


    @Override
    public void setPos(Location pos) {
        super.setPos(pos);
        hasMovedSinceLastTick = true;
    }
}
