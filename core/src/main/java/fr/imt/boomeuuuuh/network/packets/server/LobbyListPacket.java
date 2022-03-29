package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class LobbyListPacket extends Packet {

    private final String rawString;

    public LobbyListPacket(String rawString) {
        super(PacketType.LOBBY_LIST);
        this.rawString = rawString;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        // TODO decode and display
    }
}
