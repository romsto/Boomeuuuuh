package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

public class UnlockSkinPacket extends Packet {

    private final Player player;
    private final String name;

    public UnlockSkinPacket(String name, Player player) {
        super(PacketType.UNLOCK_SKIN);

        this.name = name;
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        // TODO UNLOCK SKIN
    }
}
