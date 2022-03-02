package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class JoinLobbyPacket extends Packet {

    private final Player player;

    public JoinLobbyPacket(Player player) {
        super(PacketType.JOIN_LOBBY);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        // TODO
    }
}
