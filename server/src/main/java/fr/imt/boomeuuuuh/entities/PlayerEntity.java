package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.entities.bombs.Bomb;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerEntity extends DynamicEntity {
    final Player playerRep;

    final Collection<PlayerEntity> kills;

    public PlayerEntity (Player playerRepresented, int id){
        super(id);

        playerRep = playerRepresented;
        kills = new ArrayList<>();
    }

    public Player getPlayer(){ return playerRep; } //The big faille

    public void addKill(PlayerEntity t){ kills.add(t); }
}
