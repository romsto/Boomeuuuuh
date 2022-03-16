package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.lobbies.LobbyManager;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class JoinLobbyPacket extends Packet {

    private final Player player;
    private final String name;

    public JoinLobbyPacket(Player player, String name) {
        super(PacketType.JOIN_LOBBY);

        this.player = player;
        this.name = name;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        LobbyManager.connectPlayer(player, name);
    }
}
