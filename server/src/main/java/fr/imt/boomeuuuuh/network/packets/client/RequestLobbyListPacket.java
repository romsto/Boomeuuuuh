package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.server.LobbyListPacket;
import fr.imt.boomeuuuuh.players.Player;

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
        if (!player.isAuthentified()) {
            DeclinePacket declinePacket = new DeclinePacket("You're not authenticated.");
            player.serverConnection.send(declinePacket);
            return;
        }

        player.serverConnection.send(new LobbyListPacket());
    }
}
