package fr.imt.boomeuuuuh.network.packets.both;

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.ScreenType;

public class ReadyPacket extends Packet {

    public ReadyPacket() {
        super(PacketType.READY);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        Game game = Game.getInstance();
        Lobby lobby = MyGame.getInstance().lobby;

        if (!MyGame.getInstance().logged || lobby == null || game == null)
            return;

        if (lobby.state != LobbyState.LOADING)
            return;

        lobby.state = LobbyState.PLAYING;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MyGame.getInstance().changeScreen(ScreenType.PLAY);
            }
        });
    }
}
