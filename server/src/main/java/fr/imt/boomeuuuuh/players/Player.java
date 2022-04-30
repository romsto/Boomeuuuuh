/*
 * Copyright (c) 2022.
 * Authors : Storaï R, Faure B, Mathieu A, Garry A, Nicolau T, Bregier M.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package fr.imt.boomeuuuuh.players;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.Server;
import fr.imt.boomeuuuuh.entities.PlayerEntity;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyJoiningState;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.server.KickPacket;
import fr.imt.boomeuuuuh.network.packets.server.LobbyCredentialsPacket;
import fr.imt.boomeuuuuh.network.packets.server.PlayerDataPacket;
import fr.imt.boomeuuuuh.network.packets.server.PlayerInfoPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Player {

    private int id;
    private String name;
    private PlayerData playerData;
    private boolean authentified = false;
    private boolean online = true;

    private Lobby lobby;
    private LobbyJoiningState joinedLobby = LobbyJoiningState.DISCONNETED;
    private int port;

    private int maxBombs = 1;
    private int bombPower = 2;
    private int speed = 1;
    public int currentBombs = 0;
    private int gameKills = 0;
    public int goldWonDuringGame = 0;

    private final InetAddress address;
    private final Socket serverSocket;
    public final ServerConnection serverConnection;

    /**
     * Representation of player on the server
     *
     * @param socket socket the player is attached to
     * @throws IOException thrown if connection doesn't go through
     */
    public Player(Socket socket) throws IOException {
        this.serverSocket = socket;
        this.address = socket.getInetAddress();

        this.serverConnection = new ServerConnection(this, socket);
    }

    //------Player Chars------
    public int getMaxBombs() {
        return maxBombs;
    }

    public int getBombPower() {
        return bombPower;
    }

    public int getSpeed() {
        return speed;
    }

    public int getGameKills() {
        return gameKills;
    }

    /**
     * Set the max number of bombs the player can have and communicate it to the client
     *
     * @param v value to assign
     */
    public void setMaxBombs(int v) {
        maxBombs = v;
        serverConnection.send(new PlayerInfoPacket(this));
    }

    /**
     * Increase the number of max bombs by 1 and communicate it to the client
     */
    public void increaseMaxBombs() {
        setMaxBombs(maxBombs + 1);
    }

    /**
     * Set the power of the player's bombs and communicate it to the client
     *
     * @param v value to assign
     */
    public void setBombPower(int v) {
        bombPower = v;
        serverConnection.send(new PlayerInfoPacket(this));
    }

    /**
     * Increase the power of the player's bombs by 1 and communicate it to the client
     */
    public void increasePower() {
        setBombPower(bombPower + 1);
    }

    /**
     * Set the speed of the player and communicate it to the client
     *
     * @param v value to assign
     */
    public void setSpeed(int v) {
        speed = v;
        serverConnection.send(new PlayerInfoPacket(this));
    }

    /**
     * Increase the speed of the player by 1 and communicate it to the client
     */
    public void increaseSpeed() {
        setSpeed(speed + 1);
    }

    /**
     * Set the number of kills of the player, update the max kill streak and communicate it to the client
     *
     * @param v value to assign
     */
    public void setGameKills(int v) {
        gameKills = v;
        playerData.addKills(v);
        goldWonDuringGame++;
        if (playerData.getMaxkillstreak() < gameKills)
            playerData.setMaxkillstreak(gameKills);
        serverConnection.send(new PlayerInfoPacket(this));
    }

    /**
     * Increase the number of kills of the player by 1, update the max kill streak and communicate it to the client
     */
    public void increaseGameKills() {
        setGameKills(gameKills + 1);
    }
    //------------------------

    /**
     * Get the player current IP address
     *
     * @return InetAddress
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * Get the player name if it's logged
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the player id
     *
     * @return player id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the current player lobby. May be null
     *
     * @return Lobby
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Get the lobby joining state. Used when a player join a lobby
     *
     * @return LobbyJoiningState
     */
    public LobbyJoiningState getJoinedLobbyState() {
        return joinedLobby;
    }

    /**
     * Check if the player is logged
     *
     * @return boolean
     */
    public boolean isAuthentified() {
        return authentified;
    }

    /**
     * Link the current player to a registered account, if the link fails, a decline packet is communicated to the client
     *
     * @param username username of the account
     * @param password password of the account
     * @return true if the player was linked to the account, if false possible reasons are : Player already connected, wrong credentials
     */
    public boolean authenticate(String username, String password) {
        if (Server.getPlayers().stream().anyMatch(player -> player.isAuthentified() && player.getName().equalsIgnoreCase(username))) {
            serverConnection.send(new DeclinePacket("You are already connected..."));
            return false;
        }
        boolean success = Boomeuuuuh.database.login(username, password);
        if (success) {
            authentified = true;
            playerData = Boomeuuuuh.database.getPlayerData(username);
            playerData.setPlayer(this);
            name = username;

            this.serverConnection.send(new PlayerDataPacket(this));
            return true;
        }
        serverConnection.send(new DeclinePacket("Wrong credentials..."));
        return false;
    }

    /**
     * Return the player data
     *
     * @return Playerdata
     */
    public PlayerData getPlayerData() {
        return playerData;
    }

    /**
     * Get the port the player is using to communicate through UDP
     *
     * @return int
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the current player UDP port
     *
     * @param port int
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set the current player's joining state
     *
     * @param state LobbyJoiningState
     */
    public void setJoinedLobbyState(LobbyJoiningState state) {
        this.joinedLobby = state;
    }

    /**
     * Check if the player is in a lobby
     *
     * @return boolean
     */
    public boolean isInLobby() {
        return lobby != null && joinedLobby != LobbyJoiningState.DISCONNETED;
    }

    /**
     * Make the player join a lobby, the player can't join two lobbies simultaneously
     * The client receives the lobby information and awaits port assignment from the lobby
     *
     * @param lobby lobby to join
     */
    public void joinLobby(Lobby lobby) {
        if (isInLobby()) {
            leaveLobby("Already in a lobby");
        }
        this.lobby = lobby;
        this.joinedLobby = LobbyJoiningState.WAITING_PORT;
        serverConnection.send(new LobbyCredentialsPacket(lobby.getUdpPort()));
    }

    /**
     * Remove a player from a lobby, client receives the information through a KickPacket
     *
     * @param reason reason why the player was removed
     */
    public void leaveLobby(String reason) {
        KickPacket kickPacket = new KickPacket(reason);
        getLobby().getLobbyConnection().send(this, kickPacket);
        lobby.removePlayer(this);
        lobby = null;
        joinedLobby = LobbyJoiningState.DISCONNETED;
    }

    /**
     * Disconnect the player from the server, if is in a lobby, kick the player from it
     */
    public void disconnect() {
        if (isInLobby())
            lobby.removePlayer(this);
        online = false;
        serverConnection.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equalsIgnoreCase(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    //-------------------------GAME------------------------
    PlayerEntity myEntity;

    /**
     * Get player entity associated with this player in game
     *
     * @return player entity associated with this player in game
     */
    public PlayerEntity getEntity() {
        return myEntity;
    }

    /**
     * Set player entity associated with this player in game
     *
     * @param e entity to set
     */
    public void setEntity(PlayerEntity e) {
        myEntity = e;
    }
    //-----------------------------------------------------
}

