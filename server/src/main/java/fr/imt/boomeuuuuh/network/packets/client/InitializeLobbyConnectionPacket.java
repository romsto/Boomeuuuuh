package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.lobbies.LobbyJoiningState;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.server.SuccessfullyJoinedPacket;

public class InitializeLobbyConnectionPacket extends Packet {

    private final int port;
    private final Player player;

    public InitializeLobbyConnectionPacket(int port, Player player) {
        super(PacketType.INITIALIZE_LOBBY_CONNECTION);

        this.port = port;
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Never used on server-side
        return null;
    }

    @Override
    public void handle() {
        player.setPort(port);
        player.setJoinedLobbyState(LobbyJoiningState.CONNECTED);
        player.serverConnection.send(new SuccessfullyJoinedPacket());
    }
}
