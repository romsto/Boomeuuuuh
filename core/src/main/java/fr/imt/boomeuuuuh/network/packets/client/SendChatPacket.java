package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class SendChatPacket extends Packet {

    private final String message;

    public SendChatPacket(String message) {
        super(PacketType.SEND_CHAT);

        this.message = message;
    }

    @Override
    protected byte[] encode() {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Shouldn't be handled client-side
    }
}
