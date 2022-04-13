package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.MyGame;

import java.lang.reflect.InvocationTargetException;

public enum ScreenType {

    LOADING(LoadingScreen.class),
    LOBBY(LobbyScreen.class),
    LOBBY_SELECTION(LobbySelectionScreen.class),
    LOG_IN(LoginScreen.class),
    MAIN_MENU(MainMenuScreen.class),
    PREFERENCES(PreferencesScreen.class),
    PLAY(PlayScreen.class),
    CONNECT(IPAddressScreen.class),
    CREDITS(CreditsScreen.class);

    private final Class<? extends Screen> screenClass;

    ScreenType(Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }

    public Screen instantiate(MyGame myGame) {
        Screen newInstance = null;
        try {
            newInstance = (Screen) screenClass.getDeclaredConstructors()[0].newInstance(myGame);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInstance;
    }
}
