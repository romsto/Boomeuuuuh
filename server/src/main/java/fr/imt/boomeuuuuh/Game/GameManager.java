package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.entities.bombs.*;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    //Global references
    private final Lobby lobby;
    private final List<Entity> entityList;

    //Map references
    String mapID;
    int mapHeight;
    int mapWidth;
    private final Entity[][] baseMap;

    //Id references
    private int lastID;

    public GameManager(Lobby lobby, String mapID){
        //Set vars
        this.lobby = lobby;
        entityList = new ArrayList<Entity>();

        //Load Map
        this.mapID = mapID;
        //TODO : Load Map from mapID
        mapWidth = 100;
        mapHeight = 100;
        baseMap = new Entity[mapWidth][mapHeight];
    }

    //------------------------BOMBS------------------------
    public void placeBomb(Player origin, Bomb bombType){}
    //-----------------------------------------------------
}
