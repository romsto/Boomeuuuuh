package fr.imt.boomeuuuuh.entities;

public abstract class Bomb extends MovableEntity {


    public Bomb(int id) {
        super(id);
    }

    @Override
    public abstract void draw();
}
