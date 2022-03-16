package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyManager;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class LobbyListPacket extends Packet {

    private final String rawString;

    public LobbyListPacket() {
        super(PacketType.LOBBY_LIST);

        StringBuilder builder = new StringBuilder();
        String delimiter = "";
        for (Lobby lobby : LobbyManager.getLobbies()) {
            builder.append(delimiter).append(lobby.getName()).append("|`").append(lobby.getPlayers().size()).append("|`").append(lobby.isOpen() ? 1 : 0);
            delimiter = "~`|";
        }

        this.rawString = builder.toString();
    }

    @Override
    protected byte[] encode() {
        return rawString.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Should be handled on server side
    }
}
