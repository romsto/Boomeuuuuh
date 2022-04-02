package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class DisconnectPacket extends Packet {

    private final Player player;

    public DisconnectPacket(Player player) {
        super(PacketType.DISCONNECT);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be encoded server-side
        return null;
    }

    @Override
    public void handle() {
        player.disconnect();//Handled by player initially
    }
}
