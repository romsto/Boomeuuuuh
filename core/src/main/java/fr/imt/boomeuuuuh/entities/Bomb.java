package fr.imt.boomeuuuuh.entities;

public class Bomb extends MovableEntity {

    int power=1;


    public void setPower(int power) {
        this.power = power;
    }




    public Bomb(int id) {
        super(id);
    }

}
