package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

public class LogInPacket extends Packet {

    private final Player player;
    private final String username, password;

    public LogInPacket(Player player, String username, String password) {
        super(PacketType.LOGIN);

        this.player = player;
        this.username = username;
        this.password = password;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server-side
        return null;
    }

    @Override
    public void handle() {
        if (player.isAuthentified())
            return;

        player.authenticate(username, password);
    }
}
