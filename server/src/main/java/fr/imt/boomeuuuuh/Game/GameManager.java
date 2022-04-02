package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.entities.*;
import fr.imt.boomeuuuuh.entities.bombs.Bomb;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.both.ReadyPacket;
import fr.imt.boomeuuuuh.network.packets.server.*;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Collection;

public class GameManager {

    //Global references
    private final Lobby lobby;
    private final Collection<Entity> entityList;
    private final Collection<PlayerEntity> livePlayers;
    private final Collection<PlayerEntity> deadPlayers;

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
        MapLoader loader = new MapLoader();
        Map m = loader.LoadMap(mapID, this);
        if(m == null){
            entityList = null; mapWidth = 0; mapHeight = 0; livePlayers = null; deadPlayers = null;
            endGame();
            return;
        }
        entityList = m.mapEntities;
        mapWidth = m.Width;
        mapHeight = m.Height;

        //Add players to map
        deadPlayers = new ArrayList<>();
        livePlayers = new ArrayList<>();
        for (Player p : lobby.getPlayers()) {
            PlayerEntity e = new PlayerEntity(p, getNewID());
            e.setPos(m.nextSpawn());
            p.setEntity(e);
            entityList.add(e);
            livePlayers.add(e);
        }


        //Broadcast map
        lobby.broadcastToAll(false, new StartGamePacket());

        broadcastEntities();

        broadcastReferences();

        lobby.broadcastToAll(false, new ReadyPacket());
    }

    //-----------------------------------------------------
    public void Update(){
        //---Bombs---
        for (Entity e : entityList) {
            if(e instanceof Bomb)
                ((Bomb) e).checkExplosion(entityList, this);//Faille possible
        }
        //-----------
    }

    public void UpdatePlayersPos(){
        for (PlayerEntity e : livePlayers){
            EntityMovePacket p = new EntityMovePacket(e.getId(), e.getPos());
            lobby.broadcastExcept(true, e.getPlayer(), p);
        }
    }

    private void endGame(){
        //Tell lobby to stop the game
        lobby.stopGame();
    }
    //-----------------------------------------------------
    //------------------------BOMBS------------------------
    public void placeBomb(Player origin, Location pos){// Later inclune multiple bomb types
        //Check if position is possible
        if (!checkPosition(pos)) //TODO : Check that delays in placement are not too quick
            return;

        if (origin.maxBombs >= origin.currentBombs)
            return;

        origin.currentBombs++;

        //Place bomb
        Bomb b = new Bomb(getNewID(), origin); //Need to fix this when we know more about comm and what type of bomb
        b.setPos(pos);
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

    private void broadcastEntities(Player... players) {
        boolean all = players.length == 0;
        for (Entity e : entityList) {
            EntityCreatePacket p = new EntityCreatePacket(e.getId(), getEntityRef(e), e.getPos());
            if (all)
                lobby.broadcastToAll(false, p);
            else
                lobby.broadcastTo(false, p, players);
        }
    }

    private void broadcastReferences() {
        for (Entity entity : entityList) {
            if (!(entity instanceof PlayerEntity))
                continue;
            PlayerEntity playerEntity = (PlayerEntity) entity;
            PlayerReferencePacket packet = new PlayerReferencePacket(entity.getId(), playerEntity.getPlayer().getName(), playerEntity.getPlayer().getPlayerData().getCurrentSkin());
            lobby.broadcastToAll(false, packet);
        }
    }

    public void destroyEntity(Entity e) {
        //Create destruction package and sent TCP
        EntityDestroyPacket p = new EntityDestroyPacket(e.getId());
        lobby.broadcastToAll(false, p);

        //Remove from manager
        entityList.remove(e);

        if (e instanceof PlayerEntity) {
            deadPlayers.add((PlayerEntity) e);
            livePlayers.remove((PlayerEntity) e);
        }
    }
    public void removePlayer(PlayerEntity e){
        destroyEntity(e);
    }
    //-----------------------------------------------------
    //-------------------------GET-------------------------
    public int getMapHeight(){ return mapHeight; }
    public int getMapWidth(){ return mapWidth; }
    private int getEntityRef(Entity e) {
        if (e instanceof PlayerEntity)
            return 60;
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
