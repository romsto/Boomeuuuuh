package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.entities.*;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.server.BombPlacedPacket;
import fr.imt.boomeuuuuh.network.packets.server.EntityCreatePacket;
import fr.imt.boomeuuuuh.network.packets.server.EntityDestroyPacket;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.entities.bombs.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.concurrent.TimeUnit;

public class GameManager {

    //Global references
    private final Lobby lobby;
    private final Collection<Entity> entityList;
    private final Collection<PlayerEntity> players;
    private final Collection<PlayerEntity> deadPlayers;

    //Time references
    private final long minimumTimePerUpdate = 50; //ms
    private long currentUpdateStartTime;

    //Map references
    String mapID;
    final int mapHeight;
    final int mapWidth;

    //Id references
    private int lastID;

    public GameManager(Lobby lobby, String mapID){
        //Set vars
        this.lobby = lobby;

        //Load Map
        this.mapID = mapID;
        Map m = MapLoader.LoadMap(mapID, this);
        if(m == null){
            entityList = null; mapWidth = 0; mapHeight = 0; players = null; deadPlayers = null;
            endGame();
            return;
        }
        entityList = m.mapEntities;
        mapWidth = m.Width;
        mapHeight = m.Height;

        //Broadcast map
        broadcastEntities();

        //Add players to map
        players = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        for (Player p : lobby.getPlayers())
            players.add(new PlayerEntity(p, getNewID())); //TODO : Add spawn positions
    }

    //-----------------------------------------------------
    private void Update(){
        //Set time
        currentUpdateStartTime = System.currentTimeMillis();

        //---Bombs---
        for (Entity e : entityList) {
            if(e instanceof Bomb)
                ((Bomb) e).checkExplosion(entityList, this);//Faille possible
        }
        //-----------

        try{ TimeUnit.MILLISECONDS.sleep(System.currentTimeMillis() + minimumTimePerUpdate - currentUpdateStartTime);}
        catch (Exception e) { Boomeuuuuh.logger.severe(e.getMessage()); } //NOTE : THAT SHIT SHOULD BE DONE FROM LOBBY, THAT DUDE TAKES CARE OF THE THREAD NO?
    }

    private void endGame(){

    }
    //-----------------------------------------------------
    //------------------------BOMBS------------------------
    public void placeBomb(Player origin, Location pos){// Later inclune multiple bomb types
        //Check if position is possible
        if(!checkPosition(pos)) //TODO : Check that delays in placement are not too quick
            return;

        //Place bomb
        Bomb b = new Bomb(getNewID(), origin); //Need to fix this when we know more about comm and what type of bomb
        entityList.add(b);

        BombPlacedPacket packet = new BombPlacedPacket(b.getId(), b.getPower(), b.getPos());
        lobby.broadcastToAll(false, packet);
    }
    //-----------------------------------------------------
    //------------------ENTITY MANAGEMENT------------------
    public int getNewID(){ lastID++; return lastID - 1;}
    public boolean checkPosition(Location pos){
        for (Entity e : entityList)
            if(e instanceof Bomb || e instanceof SoftBlock || e instanceof HardBlock)
                if(pos.equals(e.getPos()))
                    return false;
        return pos.comprisedInExcludingBorder(-1, mapWidth, -1, mapHeight);
    }
    private void placeEntity(Entity e){
        entityList.add(e);
        EntityCreatePacket p = new EntityCreatePacket(e.getId(), getEntityRef(e), e.getPos());
        lobby.broadcastToAll(false, p);
    }
    private void placeEntityLocal(Entity e){
        entityList.add(e);
    }
    private void broadcastEntities(Player... players){
        boolean all = players.length == 0;
        for(Entity e : entityList){
            EntityCreatePacket p = new EntityCreatePacket(e.getId(), getEntityRef(e), e.getPos());
            if (all)
                lobby.broadcastToAll(false, p);
            else
                lobby.broadcastTo(false, p, players);
        }
    }
    public void destroyEntity(Entity e){
        if(e instanceof PlayerEntity)
            ((PlayerEntity) e).Kill(this, null);

        //Create destruction package and sent TCP
        EntityDestroyPacket p = new EntityDestroyPacket(e.getId());
        lobby.broadcastToAll(false, p);

        //Remove from manager
        entityList.remove(e);
    }
    public void removePlayer(PlayerEntity p){
        if(!p.getDead())
            return;

        players.remove(p);
        deadPlayers.add(p);
    }
    //-----------------------------------------------------
    //-------------------------GET-------------------------
    public int getMapHeight(){ return mapHeight; }
    public int getMapWidth(){ return mapWidth; }
    private int getEntityRef(Entity e){
        if (e instanceof SoftBlock)
            return 50;
        if (e instanceof HardBlock)
            return 40;
        if (e instanceof PowerUp)
            return 20;
        if (e instanceof Bomb)
            return 10;
        if (e instanceof DynamicEntity)
            return 2;
        if(e instanceof StaticEntity)
            return 1;
        return 0;
    }
    //-----------------------------------------------------
}
