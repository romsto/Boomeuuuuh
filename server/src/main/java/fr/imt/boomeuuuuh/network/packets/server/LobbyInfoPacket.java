package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class LobbyInfoPacket extends Packet {

    public LobbyInfoPacket() {
        super(PacketType.LOBBY_INFO);
    }

    @Override
    protected byte[] encode() {
        // TODO
        return new byte[0];
    }

    @Override
    public void handle() {
        // Shouldn't be handled server side
    }
}
