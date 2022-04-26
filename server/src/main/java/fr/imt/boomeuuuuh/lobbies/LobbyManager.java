package fr.imt.boomeuuuuh.lobbies;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//Class for lobby manager
//  -> Has reference to lobbies
//  -> Creates lobbies
//  -> Destroys lobbies
//  -> Connects a player to a Lobby
public class LobbyManager {

    private static int lobbyID = 0;
    private final static List<Lobby> lobbies = new ArrayList<>();

    /**
     * Get a lobby by its name
     *
     * @param name of the lobby
     * @return Lobby
     */
    public static Optional<Lobby> getLobby(String name) {
        return lobbies.stream().filter(lobby -> lobby.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Get a lobby by its port
     *
     * @param port of the lobby
     * @return Lobby
     */
    public static Optional<Lobby> getLobby(int port) {
        return lobbies.stream().filter(lobby -> lobby.getUdpPort() == port).findFirst();
    }

    /**
     * Get a lobby by its owner
     *
     * @param owner of the lobby
     * @return Lobby
     */
    public static Optional<Lobby> getLobby(Player owner) {
        return lobbies.stream().filter(lobby -> lobby.getOwner().getId() == owner.getId()).findFirst();
    }

    /**
     * @return all the current lobbies
     */
    public static Collection<Lobby> getLobbies() {
        return lobbies;
    }

    /**
     * Create a lobby
     *
     * @param stPlayer owner
     * @return Lobby
     */
    public static Lobby startLobby(Player stPlayer) {
        Lobby nLobby = null;
        try {
            nLobby = new Lobby(lobbyID, stPlayer.getName() + lobbyID, stPlayer);
            lobbyID++;
            lobbies.add(nLobby);
        } catch (Exception e) {
            Boomeuuuuh.logger.severe(e.toString());
        }
        return nLobby;
    }

    //-------------------CONNECT PLAYER--------------------

    /**
     * Make a player join a lobby by its name
     *
     * @param pl   player
     * @param name of the lobby
     */
    public static void connectPlayer(Player pl, String name) {
        Optional<Lobby> l = getLobby(name);
        if (l.isPresent())
            connectPlayer(pl, l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    /**
     * Make a player join a lobby by its port
     *
     * @param pl   player
     * @param port of the lobby
     */
    public static void connectPlayer(Player pl, int port) {
        Optional<Lobby> l = getLobby(port);
        if (l.isPresent())
            connectPlayer(pl, l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    /**
     * Make a player join a lobby by its owner
     *
     * @param pl    player
     * @param owner of the lobby
     */
    public static void connectPlayer(Player pl, Player owner) {
        Optional<Lobby> l = getLobby(owner);
        if (l.isPresent())
            connectPlayer(pl, l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    /**
     * Make a player join a lobby
     *
     * @param pl          player
     * @param targetLobby lobby
     */
    private static void connectPlayer(Player pl, Lobby targetLobby) {
        if (!targetLobby.isOpen()) {
            pl.serverConnection.send(new DeclinePacket("Lobby is closed..."));
            return;
        }
        targetLobby.addPlayer(pl);
    }

    //-----------------------------------------------------
    //----------------------END LOBBY----------------------

    /**
     * Close properly a lobby
     *
     * @param name name of the lobby
     */
    public static void endLobby(String name) {
        Optional<Lobby> l = getLobby(name);
        if (l.isPresent())
            endLobby(l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    /**
     * Close properly a lobby
     *
     * @param port port of the lobby
     */
    public static void endLobby(int port) {
        Optional<Lobby> l = getLobby(port);
        if (l.isPresent())
            endLobby(l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    /**
     * Close properly a lobby
     *
     * @param owner owner of the lobby
     */
    public static void endLobby(Player owner) {
        Optional<Lobby> l = getLobby(owner);
        if (l.isPresent())
            endLobby(l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    /**
     * Close properly a lobby
     *
     * @param lobby lobby of the lobby
     */
    private static void endLobby(Lobby lobby) {
        lobby.close();
        //Remove lobby
        lobbies.removeIf(lob -> lob.getLobbyID() == lobby.getLobbyID());
    }
    //-----------------------------------------------------

    /**
     * Check if a lobby exists
     *
     * @param name of the lobby
     * @return true or false
     */
    public static boolean exists(String name) {
        return getLobby(name).isPresent();
    }

    /**
     * Check if a lobby exists
     *
     * @param port of the lobby
     * @return true or false
     */
    public static boolean exists(int port) {
        return getLobby(port).isPresent();
    }

    /**
     * Check if a lobby exists
     *
     * @param owner of the lobby
     * @return true or false
     */
    public static boolean exists(Player owner) {
        return getLobby(owner).isPresent();
    }
}
