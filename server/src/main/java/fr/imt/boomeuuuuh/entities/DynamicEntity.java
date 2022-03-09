package fr.imt.boomeuuuuh.entities;

public class DynamicEntity extends Entity{

    public DynamicEntity(int id) {
        super(id);
    }

    //-------------------------GET-------------------------
    @Override
    public boolean isMovable() { return true; }
    //-----------------------------------------------------
}
