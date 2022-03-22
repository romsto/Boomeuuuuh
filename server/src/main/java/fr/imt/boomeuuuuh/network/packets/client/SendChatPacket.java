package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.server.ReceiveChatPacket;
import fr.imt.boomeuuuuh.players.Player;

public class SendChatPacket extends Packet {

    private final Player player;
    private final String message;

    public SendChatPacket(String message, Player player) {
        super(PacketType.SEND_CHAT);

        this.message = message;
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        if (!player.isAuthentified()) {
            DeclinePacket declinePacket = new DeclinePacket("You're not authenticated.");
            player.serverConnection.send(declinePacket);
            return;
        }

        if (!player.isInLobby())
            return;

        player.getLobby().broadcastToAll(false, new ReceiveChatPacket(player.getName() + " : " + message));
    }
}
