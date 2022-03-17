package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.lobbies.LobbyManager;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

public class ChangeNamePacket extends Packet {

    private final Player player;
    private final String name;

    public ChangeNamePacket(String name, Player player) {
        super(PacketType.CHANGE_LOBBY_NAME);

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
        if (!player.isInLobby())
            return;

        if (!player.getLobby().getOwner().equals(player)) {
            player.serverConnection.send(new DeclinePacket("You're not owner of the lobby."));
            return;
        }

        if (LobbyManager.exists(name)) {
            player.serverConnection.send(new DeclinePacket("You're not owner of the lobby."));
            return;
        }

        player.getLobby().setName(name);
    }
}
