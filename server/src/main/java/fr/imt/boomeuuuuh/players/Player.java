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
    private boolean changed = true;

    private final InetAddress address;
    private final Socket serverSocket;
    public final ServerConnection serverConnection;

    public Player(Socket socket) throws IOException {
        this.serverSocket = socket;
        this.address = socket.getInetAddress();

        this.serverConnection = new ServerConnection(this, socket);
    }

    //------Player Chars------
    public int getMaxBombs(){ return maxBombs; }
    public int getBombPower(){ return bombPower; }
    public int getSpeed(){ return speed; }
    public int getGameKills(){ return gameKills; }
    public boolean infoChanged(){ return changed; }

    public void setMaxBombs(int v){
        maxBombs = v;
        changed = true;
    }
    public void increaseMaxBombs(){ setMaxBombs(maxBombs + 1); }
    public void setBombPower(int v){
        bombPower = v;
        changed = true;
    }
    public void increasePower(){ setBombPower(bombPower + 1); }
    public void setSpeed(int v){
        speed = v;
        changed = true;
    }
    public void increaseSpeed(){ setSpeed(speed + 1); }
    public void setGameKills(int v){
        gameKills = v;
        changed = true;
    }
    public void increaseGameKills(){ setGameKills(gameKills + 1); }
    public void setChanged( boolean v){
        changed = true;
    }
    //------------------------

    public InetAddress getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public LobbyJoiningState getJoinedLobbyState() {
        return joinedLobby;
    }

    public boolean isAuthentified() {
        return authentified;
    }

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

    public PlayerData getPlayerData() {
        return playerData;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setJoinedLobbyState(LobbyJoiningState state) {
        this.joinedLobby = state;
    }

    public boolean isInLobby() {
        return lobby != null && joinedLobby != LobbyJoiningState.DISCONNETED;
    }

    public void joinLobby(Lobby lobby) {
        if (isInLobby()) {
            leaveLobby("Already in a lobby");
        }
        this.lobby = lobby;
        this.joinedLobby = LobbyJoiningState.WAITING_PORT;
        serverConnection.send(new LobbyCredentialsPacket(lobby.getUdpPort()));
    }

    public void leaveLobby(String reason) {
        KickPacket kickPacket = new KickPacket(reason);
        getLobby().getLobbyConnection().send(this, kickPacket);
        lobby.removePlayer(this);
        lobby = null;
        joinedLobby = LobbyJoiningState.DISCONNETED;
    }

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

    public PlayerEntity getEntity() {
        return myEntity;
    }

    public void setEntity(PlayerEntity e) {
        myEntity = e;
    }
    //-----------------------------------------------------
}

