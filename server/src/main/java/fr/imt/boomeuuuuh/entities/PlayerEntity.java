package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.entities.bombs.Bomb;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerEntity extends DynamicEntity {
    final Player playerRep;
    boolean dead = false;

    final Collection<PlayerEntity> kills;

    public PlayerEntity (Player playerRepresented, int id){
        super(id);

        playerRep = playerRepresented;
        kills = new ArrayList<>();
    }

    public void Kill(GameManager man, Entity origin){ //Big vulnerability!!!!
        //Remove from manager
        dead = true;
        man.removePlayer(this);

        //Top up points
        if(origin instanceof Bomb)
            ((Bomb) origin).getPlayerEntity().addKill(this);

        //TODO : Finish this func
    }
    public void addKill(PlayerEntity t){ kills.add(t); }
    public boolean getDead(){ return dead; }
}
