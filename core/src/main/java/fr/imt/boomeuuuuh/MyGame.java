package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyGame extends Game {

    private SpriteBatch batch;

    private final Map<ScreenType, Screen> screens = new ConcurrentHashMap<>();
    private ScreenType currentScreenType;
    private ScreenType lastScreenType;

    @Override
    public void create() {
        batch = new SpriteBatch();

        changeScreen(ScreenType.PLAY);
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
}

