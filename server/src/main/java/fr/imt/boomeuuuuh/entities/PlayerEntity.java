package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.players.Player;

public class PlayerEntity extends DynamicEntity {
    Player playerRep;

    public PlayerEntity (Player playerRepresented, int id){
        super(id);

        playerRep = playerRepresented;
    }

    //TODO : Write this func
    public void Kill(){}
}
