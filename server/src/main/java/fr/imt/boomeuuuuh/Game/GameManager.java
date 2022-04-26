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
import java.util.List;

public class GameManager {

    //Global references
    private final Lobby lobby;
    private final Collection<Entity> entityList;
    private final List<PlayerEntity> livePlayers;
    private final Collection<PlayerEntity> deadPlayers;

    //Map references
    String mapID;
    final int mapHeight;
    final int mapWidth;

    //Id references
    private int lastID;

    public boolean ready = false;

    /**
     * Create a Game
     *
     * @param lobby linked
     * @param mapID id of the map to load
     */
    public GameManager(Lobby lobby, String mapID) {
        //Set vars
        this.lobby = lobby;

        //Load Map
        this.mapID = mapID;
        MapLoader loader = new MapLoader();
        Map m = loader.LoadMap(mapID, this);
        if (m == null) {
            entityList = null;
            mapWidth = 0;
            mapHeight = 0;
            livePlayers = null;
            deadPlayers = null;
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
            p.setMaxBombs(1);
            p.currentBombs = 0;
            p.setSpeed(1);
            p.setBombPower(1);
            p.setGameKills(0);
            p.goldWonDuringGame = 0;

            entityList.add(e);
            livePlayers.add(e);
        }


        //Broadcast map
        lobby.broadcastToAll(false, new StartGamePacket());

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        broadcastEntities();

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        broadcastReferences();

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        lobby.broadcastToAll(false, new ReadyPacket());

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }

        ready = true;
    }

    //-----------------------------------------------------

    /**
     * Called each ticks
     */
    public void Update() {
        //---Bombs---
        List<Entity> copiedList = new ArrayList<>(entityList);
        for (Entity e : copiedList) {
            if (e instanceof Bomb)
                ((Bomb) e).checkExplosion(copiedList, this);//Faille possible
        }
        //-----------
    }

    /**
     * Called to update the player location
     */
    public void UpdatePlayersPos() {
        for (PlayerEntity e : new ArrayList<>(livePlayers)) {
            if (!e.hasMovedSinceLastTick)
                continue;
            e.hasMovedSinceLastTick = false;
            EntityMovePacket p = new EntityMovePacket(e.getId(), e.getPos(), e.getPlayer().getSpeed());
            lobby.broadcastExcept(false, e.getPlayer(), p);
        }
    }

    /**
     * Stop the game
     */
    private void endGame() {
        //Tell lobby to stop the game
        lobby.stopGame();
    }

    //-----------------------------------------------------
    //------------------------BOMBS------------------------

    /**
     * Place a bomb
     *
     * @param origin player who placed it
     * @param pos    to place
     */
    public void placeBomb(Player origin, Location pos) {
        //Check if position is possible
        if (!checkPosition(pos))
            return;

        if (origin.getMaxBombs() <= origin.currentBombs)
            return;

        origin.currentBombs++;

        //Place bomb
        Bomb b = new Bomb(getNewID(), origin); //Need to fix this when we know more about comm and what type of bomb
        b.setPos(pos);
        entityList.add(b);

        BombPlacedPacket packet = new BombPlacedPacket(b.getId(), b.getPower() + 1, b.getPos());
        lobby.broadcastToAll(false, packet);
    }

    //-----------------------------------------------------
    //------------------ENTITY MANAGEMENT------------------

    /**
     * Get next entity id (unique)
     *
     * @return unique id
     */
    public int getNewID() {
        lastID++;
        return lastID - 1;
    }

    /**
     * Check if you can place a bomb
     *
     * @param pos to palce
     * @return true or false
     */
    public boolean checkPosition(Location pos) {
        for (Entity e : entityList)
            if (e instanceof Bomb || e instanceof SoftBlock || e instanceof HardBlock)
                if (pos.equals(e.getPos()))
                    return false;
        return pos.comprisedInExcludingBorder(-1, mapWidth, -1, mapHeight);
    }

    /**
     * Returns the list of all the entities
     *
     * @return entity list
     */
    public Collection<Entity> getEntityList() {
        return entityList;
    }

    /**
     * Spawns an entity
     *
     * @param e to spawn
     */
    public void placeEntity(Entity e) {
        entityList.add(e);
        EntityCreatePacket p = new EntityCreatePacket(e.getId(), getEntityRef(e), e.getPos());
        lobby.broadcastToAll(false, p);
    }

    /**
     * Sends entities to all the players
     *
     * @param players to send the entities
     */
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

    /**
     * Sends the player's references
     */
    private void broadcastReferences() {
        for (Entity entity : entityList) {
            if (!(entity instanceof PlayerEntity))
                continue;
            PlayerEntity playerEntity = (PlayerEntity) entity;
            PlayerReferencePacket packet = new PlayerReferencePacket(entity.getId(), playerEntity.getPlayer().getName(), playerEntity.getPlayer().getPlayerData().getCurrentSkin());
            lobby.broadcastToAll(false, packet);
        }
    }

    /**
     * Remove an entity
     *
     * @param e to remove
     */
    public void destroyEntity(Entity e) {
        if (!entityList.contains(e))
            return;
        //Create destruction package and sent TCP
        EntityDestroyPacket p = new EntityDestroyPacket(e.getId());
        lobby.broadcastToAll(false, p);

        //Remove from manager
        entityList.remove(e);

        if (e instanceof PlayerEntity) {
            deadPlayers.add((PlayerEntity) e);
            livePlayers.remove((PlayerEntity) e);

            if (livePlayers.size() <= 1) {
                if (livePlayers.size() == 1) {
                    String winner = "\n\n" + livePlayers.get(0).getPlayer().getName() + " won the game with " + livePlayers.get(0).getPlayer().getGameKills() + " kills !";
                    lobby.addToChat(winner);
                    livePlayers.get(0).getPlayer().getPlayerData().addWin();
                    livePlayers.get(0).getPlayer().goldWonDuringGame += 5;
                    ReceiveChatPacket receiveChatPacket = new ReceiveChatPacket(winner);
                    lobby.broadcastToAll(false, receiveChatPacket);
                }
                endGame();
            }
        }
    }

    //-----------------------------------------------------
    //-------------------------GET-------------------------

    /**
     * @return map's height
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * @return map width
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Get entity id by its type
     *
     * @param e to get
     * @return id
     */
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
        if (e instanceof StaticEntity)
            return 1;
        return 0;
    }
    //-----------------------------------------------------
}
