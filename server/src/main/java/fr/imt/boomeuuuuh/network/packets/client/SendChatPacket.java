package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

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
        // TODO send to lobby then broadcast to everyone
    }
}
