package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.ScreenType;

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
        MyGame myGame = MyGame.getInstance();
        if (myGame.getCurrentScreenType() == ScreenType.LOBBY)
            myGame.changeScreen(ScreenType.LOBBY_SELECTION);

        Lobby lobby = myGame.lobby;
        if (lobby == null)
            return;
        lobby.lobbyConnection.close();
        if (lobby.game != null)
            lobby.game.dispose();
        lobby.game = null;

        myGame.lobby = null;
    }
}
