package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyGame extends Game {

    private final Map<ScreenType, Screen> screens = new ConcurrentHashMap<>();
    private ScreenType currentScreenType;

    @Override
    public void create() {
        changeScreen(ScreenType.MAIN_MENU);
    }

    public ScreenType getCurrentScreenType() {
        return currentScreenType;
    }

    public Screen getCurrentScreen() {
        return getScreen(currentScreenType);
    }

    public Screen getScreen(ScreenType screenType) {
        return screens.get(screenType);
    }

    public void changeScreen(ScreenType screenType) {
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
}

