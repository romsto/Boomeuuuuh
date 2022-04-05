package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;

public class PlayerChangeBlocPacket extends Packet {

    private final Player player;
    private final Location newLocation;

    public PlayerChangeBlocPacket(Player player, Location newLocation) {
        super(PacketType.CHANGE_BLOC);

        this.player = player;
        this.newLocation = newLocation;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server-side
        return null;
    }

    @Override
    public void handle() {
        if (!player.isInLobby())
            return;

        Lobby lobby = player.getLobby();
        if (lobby.getState() != LobbyState.PLAYING)
            return;
        player.getEntity().setPos(newLocation);
    }
}
