package fr.imt.boomeuuuuh.lobbies;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LobbyManager {

    private final static List<Lobby> lobbies = new ArrayList<>();

    public static Optional<Lobby> getLobby(String name) {
        return lobbies.stream().filter(lobby -> lobby.getName().toLowerCase().equals(name.toLowerCase())).findFirst();
    }

    public static Optional<Lobby> getLobby(int port) {
        return lobbies.stream().filter(lobby -> lobby.getUdpPort() == port).findFirst();
    }

    public static boolean exists(String name) {
        return getLobby(name).isPresent();
    }

    public static boolean exists(int port) {
        return getLobby(port).isPresent();
    }
}
