package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class CreateLobbyPacket extends Packet {

    private final Player player;
    private final String name;

    public CreateLobbyPacket(Player player, String name) {
        super(PacketType.CREATE_LOBBY);

        this.player = player;
        this.name = name;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be encoded server side
        return null;
    }

    @Override
    public void handle() {
        // TODO create a lobby
        // TODO decline or send accept
    }
}
