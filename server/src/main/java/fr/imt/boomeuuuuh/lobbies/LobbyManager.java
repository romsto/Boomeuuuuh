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

    public static Optional<Lobby> getLobby(String name) {
        return lobbies.stream().filter(lobby -> lobby.getName().equalsIgnoreCase(name)).findFirst();
    }

    public static Optional<Lobby> getLobby(int port) {
        return lobbies.stream().filter(lobby -> lobby.getUdpPort() == port).findFirst();
    }

    public static Optional<Lobby> getLobby(Player owner) {
        return lobbies.stream().filter(lobby -> lobby.getOwner().getId() == owner.getId()).findFirst();
    }

    public static Collection<Lobby> getLobbies() {
        return lobbies;
    }

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
    public static void connectPlayer(Player pl, String name) {
        Optional<Lobby> l = getLobby(name);
        if (l.isPresent())
            connectPlayer(pl, l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    public static void connectPlayer(Player pl, int port) {
        Optional<Lobby> l = getLobby(port);
        if (l.isPresent())
            connectPlayer(pl, l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    public static void connectPlayer(Player pl, Player owner) {
        Optional<Lobby> l = getLobby(owner);
        if (l.isPresent())
            connectPlayer(pl, l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    private static void connectPlayer(Player pl, Lobby targetLobby) {
        if (!targetLobby.isOpen()) {
            pl.serverConnection.send(new DeclinePacket("Lobby is closed..."));
            return;
        }
        targetLobby.addPlayer(pl);
    }

    //-----------------------------------------------------
    //----------------------END LOBBY----------------------
    public static void endLobby(String name) {
        Optional<Lobby> l = getLobby(name);
        if (l.isPresent())
            endLobby(l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    public static void endLobby(int port) {
        Optional<Lobby> l = getLobby(port);
        if (l.isPresent())
            endLobby(l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    public static void endLobby(Player owner) {
        Optional<Lobby> l = getLobby(owner);
        if (l.isPresent())
            endLobby(l.get());
        else {
            Boomeuuuuh.logger.severe("Lobby isn't registered to the manager");
        }
    }

    private static void endLobby(Lobby lobby) {
        lobby.close();
        //Remove lobby
        lobbies.removeIf(lob -> lob.getLobbyID() == lobby.getLobbyID());
    }
    //-----------------------------------------------------

    public static boolean exists(String name) {
        return getLobby(name).isPresent();
    }

    public static boolean exists(int port) {
        return getLobby(port).isPresent();
    }

    public static boolean exists(Player owner) {
        return getLobby(owner).isPresent();
    }
}
