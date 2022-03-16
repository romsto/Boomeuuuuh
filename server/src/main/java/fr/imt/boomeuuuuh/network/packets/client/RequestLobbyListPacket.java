package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.server.LobbyListPacket;

public class RequestLobbyListPacket extends Packet {

    private final Player player;

    public RequestLobbyListPacket(Player player) {
        super(PacketType.REQUEST_LOBBY_LIST);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Never used on server-side
        return null;
    }

    @Override
    public void handle() {
        player.serverConnection.send(new LobbyListPacket());
    }
}
