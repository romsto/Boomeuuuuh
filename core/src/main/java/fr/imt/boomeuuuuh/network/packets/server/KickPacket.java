package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class KickPacket extends Packet {

    private final String reason;

    public KickPacket(String reason) {
        super(PacketType.KICK);

        this.reason = reason;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        // TODO LEAVE LOBBY
        Lobby lobby = MyGame.getInstance().lobby;
        lobby.lobbyConnection.close();
    }
}
