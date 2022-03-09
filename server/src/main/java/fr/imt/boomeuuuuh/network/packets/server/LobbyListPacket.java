package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LobbyListPacket extends Packet {

    private String rawString;

    public LobbyListPacket() {
        super(PacketType.LOBBY_LIST);

        List<Lobby> lobbies = new ArrayList<>(); // TODO fetch all lobbies here
        StringBuilder builder = new StringBuilder();

        String separator = "";
        for (Lobby lobby : lobbies) {
            builder.append(separator).append(lobby.toString()); // Todo create the toString method
            separator = "\u3052";
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
