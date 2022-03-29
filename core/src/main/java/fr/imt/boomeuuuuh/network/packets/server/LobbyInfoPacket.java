package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.util.Arrays;
import java.util.List;

public class LobbyInfoPacket extends Packet {

    private final String name;
    private final String owner;
    private final boolean open;
    private final List<String> players;

    public LobbyInfoPacket(String rawData) {
        super(PacketType.LOBBY_INFO);
        String[] data = rawData.split("[|]");
        this.name = data[0];
        this.owner = data[1];
        this.open = data[2].equals("1");
        String[] rawPlayers = data[3].split("/");
        this.players = Arrays.asList(rawPlayers);
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        if (MyGame.getInstance().lobby == null)
            MyGame.getInstance().lobby = new Lobby();

        Lobby lobby = MyGame.getInstance().lobby;
        lobby.name = name;
        lobby.owner = owner;
        lobby.isOwner = MyGame.getInstance().username.equalsIgnoreCase(owner);
        lobby.players = players;
        lobby.open = open;
    }
}
