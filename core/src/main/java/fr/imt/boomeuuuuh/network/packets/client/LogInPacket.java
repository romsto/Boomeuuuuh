package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class LogInPacket extends Packet {

    private final String username, password;

    public LogInPacket(String username, String password) {
        super(PacketType.LOGIN);

        this.username = username;
        this.password = password;
    }

    @Override
    protected byte[] encode() {
        return (username + "Ǝ" + password).getBytes(StandardCharsets.UTF_16);
    }

    @Override
    public void handle() {
        // Shouldn't be handled client-side
    }
}
