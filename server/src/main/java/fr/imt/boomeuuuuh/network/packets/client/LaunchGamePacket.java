package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.lobbies.LobbyState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

public class LaunchGamePacket extends Packet {

    private final Player player;

    public LaunchGamePacket(Player player) {
        super(PacketType.LAUNCH_GAME);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        if (!player.isAuthentified()) {
            DeclinePacket declinePacket = new DeclinePacket("You're not authenticated.");
            player.serverConnection.send(declinePacket);
            return;
        }

        if (!player.isInLobby())
            return;

        if (!player.getLobby().getOwner().equals(player)) {
            player.serverConnection.send(new DeclinePacket("You're not owner of the lobby."));
            return;
        }

        if (player.getLobby().getState() == LobbyState.PLAYING) {
            player.serverConnection.send(new DeclinePacket("The game is already running."));
            return;
        }

        if (player.getLobby().getPlayers().size() <= 1) {
            player.serverConnection.send(new DeclinePacket("There is not enough player..."));
            return;
        }

        player.getLobby().startGame("map1");
    }
}
