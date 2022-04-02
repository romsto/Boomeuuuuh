package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class LeavePacket extends Packet {

    private final Player player;

    public LeavePacket(Player player) {
        super(PacketType.LEAVE);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be encoded server-side
        return null;
    }

    @Override
    public void handle() {
        if(player.getLobby() == null){
            DeclinePacket declinePacket = new DeclinePacket("You're not in a lobby.");
            player.serverConnection.send(declinePacket);
            return;
        }

        player.leaveLobby("Player request");//Handled by player
    }
}
