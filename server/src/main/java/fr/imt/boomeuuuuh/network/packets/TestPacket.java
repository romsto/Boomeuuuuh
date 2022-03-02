package fr.imt.boomeuuuuh.network.packets;

import java.nio.charset.StandardCharsets;

public class TestPacket extends Packet {

    private String message;

    public TestPacket(String message) {
        super(PacketType.TEST);
        this.message = message;
    }

    @Override
    byte[] encode() {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    public String getMessage() {
        return message;
    }
}
