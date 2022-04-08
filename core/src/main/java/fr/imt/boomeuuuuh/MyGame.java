package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.both.AlivePacket;
import fr.imt.boomeuuuuh.screens.ScreenType;
import fr.imt.boomeuuuuh.utils.AppPreferences;
import fr.imt.boomeuuuuh.utils.ConfigFile;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyGame extends Game {

    public static InetAddress SERVER_ADDRESS = ConfigFile.ADDRESS;
    public static int SERVER_PORT_TCP = ConfigFile.PORT;
    private static MyGame instance;

    private SpriteBatch batch;

    private final Map<ScreenType, Screen> screens = new ConcurrentHashMap<>();
    private ScreenType currentScreenType;
    private ScreenType lastScreenType;

    private final AppPreferences preferences;

    public ServerConnection serverConnection;
    public Lobby lobby;
    public String username;
    public boolean logged = false;
    public PlayerData playerData;
    public boolean connected = false;

    public MyGame() {
        instance = this;
        preferences = new AppPreferences();
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

    public AppPreferences getPreferences() {
        return preferences;
    }

    public void changeScreen(ScreenType screenType) {
        lastScreenType = currentScreenType;
        if (lastScreenType != null && screens.containsKey(lastScreenType)) {
            screens.get(lastScreenType).dispose();
            screens.remove(lastScreenType);
        }

        Screen screen = null;
        if (!screens.containsKey(screenType)) {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (connected)
                        serverConnection.send(new AlivePacket());

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();
    }
}