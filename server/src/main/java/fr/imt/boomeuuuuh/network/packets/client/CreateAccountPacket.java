package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

public class CreateAccountPacket extends Packet {

    private final Player player;
    private final String username, password;

    /**
     * Packet received when a client requests to create an account
     * @param player player that made the request
     * @param username new account username
     * @param password new account password
     */
    public CreateAccountPacket(Player player, String username, String password) {
        super(PacketType.CREATE_ACCOUNT);

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

        if (Boomeuuuuh.database.usernameAlreadyExists(username)) {
            DeclinePacket packet = new DeclinePacket("Username already taken...");
            player.serverConnection.send(packet);
            return;
        }

        Boomeuuuuh.database.createAccount(username, password);
        player.authenticate(username, password);
    }
}
