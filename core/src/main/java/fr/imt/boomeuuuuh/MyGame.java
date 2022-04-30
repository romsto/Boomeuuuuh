/*
 * Copyright (c) 2022.
 * Authors : Storaï R, Faure B, Mathieu A, Garry A, Nicolau T, Bregier M.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.both.AlivePacket;
import fr.imt.boomeuuuuh.screens.ScreenType;
import fr.imt.boomeuuuuh.utils.AppPreferences;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyGame extends Game {

    public static InetAddress SERVER_ADDRESS;
    public static int SERVER_PORT_TCP;
    private static MyGame instance;

    private final Map<ScreenType, Screen> screens = new ConcurrentHashMap<>();
    private ScreenType currentScreenType;
    private ScreenType lastScreenType;

    public final AppPreferences preferences;

    public ServerConnection serverConnection;
    public Lobby lobby;
    public String username;
    public boolean logged = false;
    public PlayerData playerData;
    public boolean connected = false;

    /**
     * Main instance
     */
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

    /**
     * Get a Drawable by its path
     *
     * @param path to get
     * @return Drawable
     */
    public static Drawable getDrawable(String path) {
        Texture texture = new Texture(Gdx.files.internal(path));
        TextureRegion textureRegion = new TextureRegion(texture);
        return new TextureRegionDrawable(texture);
    }

    /**
     * Check if the player is in a lobby (or in game)
     *
     * @return boolean
     */
    public boolean hasLobby() {
        return lobby != null;
    }

    /**
     * @return current screen
     */
    public ScreenType getCurrentScreenType() {
        return currentScreenType;
    }

    /**
     * @return current instance
     */
    public Screen getCurrentScreen() {
        return getScreen(currentScreenType);
    }

    /**
     * @return last used screen
     */
    public ScreenType getLastScreenType() {
        return lastScreenType;
    }

    /**
     * @return last used screen
     */
    public Screen getLastScreen() {
        return getScreen(lastScreenType);
    }

    /**
     * Get the screen instance of a screenType
     *
     * @param screenType to check
     * @return Screen
     */
    public Screen getScreen(ScreenType screenType) {
        return screens.get(screenType);
    }

    /**
     * @return preferences of the player
     */
    public AppPreferences getPreferences() {
        return preferences;
    }

    @Override
    public void create() {
        changeScreen(ScreenType.MAIN_MENU);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (connected)
                        serverConnection.send(new AlivePacket());

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    /**
     * Change the current displayed Screen
     *
     * @param screenType to display
     */
    public void changeScreen(ScreenType screenType) {
        lastScreenType = currentScreenType;
        if (lastScreenType != null && screens.containsKey(lastScreenType)) {
            screens.get(lastScreenType).dispose();
            screens.remove(lastScreenType);
        }

        Screen screen;
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
}