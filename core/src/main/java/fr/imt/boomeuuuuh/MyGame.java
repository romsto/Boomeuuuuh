package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyGame extends Game {

    public static InetAddress SERVER_ADDRESS;
    public static int SERVER_PORT_TCP;
    private static MyGame instance;

    private SpriteBatch batch;

    private final Map<ScreenType, Screen> screens = new ConcurrentHashMap<>();
    private ScreenType currentScreenType;
    private ScreenType lastScreenType;

    static {
        try {
            SERVER_ADDRESS = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        SERVER_PORT_TCP = 301;
    }

    public ServerConnection serverConnection;
    public Lobby lobby;
    public String username;
    public boolean logged = false;
    public PlayerData playerData;

    public MyGame() {
        instance = this;
    }

    /**
     * Gets the Singleton Instance
     *
     * @return MyGame
     */
    public static MyGame getInstance() {
        return instance;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ScreenType getCurrentScreenType() {
        return currentScreenType;
    }

    public Screen getCurrentScreen() {
        return getScreen(currentScreenType);
    }

    public ScreenType getLastScreenType() {
        return lastScreenType;
    }

    public Screen getLastScreen() {
        return getScreen(lastScreenType);
    }

    public Screen getScreen(ScreenType screenType) {
        return screens.get(screenType);
    }

    public void changeScreen(ScreenType screenType) {
        lastScreenType = currentScreenType;

        Screen screen = null;
        if (screens.containsKey(screenType)) {
            screen = screenType.instantiate(this);
            screens.put(screenType, screen);
        } else {
            screen = screens.get(screenType);
            if (screen == null) {
                screen = screenType.instantiate(this);
                screens.put(screenType, screen);
            }
        }

        currentScreenType = screenType;
        setScreen(screen);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        changeScreen(ScreenType.MAIN_MENU);

        try {
            serverConnection = new ServerConnection(SERVER_ADDRESS, SERVER_PORT_TCP);
            //serverConnection.send(new LogInPacket("test", "tes3"));
            //serverConnection.send(new LogInPacket("test", "test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

