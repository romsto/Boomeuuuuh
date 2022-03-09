package fr.imt.boomeuuuuh.network.packets.both;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class TestPacket extends Packet {

    private String message;

    public TestPacket(String message) {
        super(PacketType.TEST);
        this.message = message;
    }

    @Override
    protected byte[] encode() {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void handle() {
        System.out.println(getMessage());
        Boomeuuuuh.logger.info(getMessage());
    }
}
