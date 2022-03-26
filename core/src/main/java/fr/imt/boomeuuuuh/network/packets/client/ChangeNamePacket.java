package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class ChangeNamePacket extends Packet {

    private final String name;

    public ChangeNamePacket(String name) {
        super(PacketType.CHANGE_LOBBY_NAME);

        this.name = name;
    }

    @Override
    protected byte[] encode() {
        return name.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Shouldn't be handled client side
    }
}
