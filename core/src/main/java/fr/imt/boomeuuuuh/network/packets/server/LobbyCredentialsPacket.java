package fr.imt.boomeuuuuh.network.packets.server;

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.LobbyConnection;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.net.SocketException;

public class LobbyCredentialsPacket extends Packet {

    private final int port;

    public LobbyCredentialsPacket(int port) {
        super(PacketType.LOBBY_CREDENTIALS);
        this.port = port;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        try {
            MyGame.getInstance().lobby = new Lobby();
            MyGame.getInstance().lobby.lobbyConnection = new LobbyConnection(MyGame.SERVER_ADDRESS, port);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    MyGame.getInstance().changeScreen(ScreenType.LOBBY);
                }
            });
        } catch (SocketException e) {
            System.out.println("Error while connecting to the server " + e.getCause());

            final MyGame myGame = MyGame.getInstance();
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    myGame.changeScreen(ScreenType.LOBBY_SELECTION);
                }
            });

            Lobby lobby = myGame.lobby;
            lobby.game = null;
            myGame.lobby = null;
        }
    }
}
