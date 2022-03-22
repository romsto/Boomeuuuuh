package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

public class PlayerDataPacket extends Packet {

    public PlayerDataPacket(Player player) {
        super(PacketType.PLAYER_DATA);
    }

    @Override
    protected byte[] encode() {
        // TODO
        return new byte[0];
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
