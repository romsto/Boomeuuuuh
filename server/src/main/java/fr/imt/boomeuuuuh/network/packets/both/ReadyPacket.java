package fr.imt.boomeuuuuh.network.packets.both;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

public class ReadyPacket extends Packet {

    private Player player;

    public ReadyPacket(Player player) {
        super(PacketType.READY);

        this.player = player;
    }

    public ReadyPacket() {
        super(PacketType.READY);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        // TODO Handle this packet
    }
}
