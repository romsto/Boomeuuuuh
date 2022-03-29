package fr.imt.boomeuuuuh.lobbies;

import fr.imt.boomeuuuuh.network.LobbyConnection;

import java.util.ArrayList;
import java.util.Collection;

public class Lobby {

    private final LobbyState state = LobbyState.WAITING;
    public String name;
    public String owner;
    public boolean open = true;
    public boolean isOwner = false;
    public Collection<String> players = new ArrayList<>();
    public String chat = "";
    public LobbyConnection lobbyConnection;
}
