package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

import java.nio.charset.StandardCharsets;

public class LobbyInfoPacket extends Packet {

    private final String rawData;

    public LobbyInfoPacket(Lobby lobby) {
        // TODO CHANGE CLIENT ID WITH CLIENT USERNAME
        super(PacketType.LOBBY_INFO);
        StringBuilder builder = new StringBuilder(lobby.getName()).append("ǥ").append(lobby.getOwner().getId()).append("ǥ").append(lobby.isOpen() ? 1 : 0).append("ǥ");
        String delimiter = "";
        for (Player player : lobby.getPlayers()) {
            builder.append(delimiter).append(player.getName());
            delimiter = "ǭ";
        }
        this.rawData = builder.toString();
    }

    @Override
    protected byte[] encode() {
        return rawData.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Shouldn't be handled server side
    }
}
