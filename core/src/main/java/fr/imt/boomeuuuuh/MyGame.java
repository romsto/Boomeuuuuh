package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.imt.boomeuuuuh.screens.*;

public class MyGame extends Game {

    private LoadingScreen loadingScreen;
    private MainMenuScreen mainMenuScreen;
    private OptionScreen optionScreen;
    private PreferencesScreen preferencesScreen;
    private LobbyScreen lobbyScreen;
    private LobbySelectionScreen lobbySelectionScreen;
    private PlayScreen playScreen;

    public final static int MAINMENU = 0;
    public final static int OPTIONS = 1;
    public final static int PREFERENCES = 2;
    public final static int LOBBY = 3;
    public final static int LOBBYSELECTION = 4;
    public final static int PLAY = 5;

    @Override
    public void create() {
        mainMenuScreen = new MainMenuScreen(this);
        lobbyScreen = new LobbyScreen(this);
        lobbySelectionScreen = new LobbySelectionScreen(this);
        loadingScreen = new LoadingScreen(this);
        playScreen = new PlayScreen(this);
        optionScreen = new OptionScreen(this);
        preferencesScreen = new PreferencesScreen(this);

        setScreen(mainMenuScreen);
    }

    public void changeScreen(int screen){
        switch(screen){
            case MAINMENU:
                if(mainMenuScreen == null) mainMenuScreen = new MainMenuScreen(this);
                break;
            case PREFERENCES:
                if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            case OPTIONS:
                if(optionScreen == null) optionScreen = new OptionScreen(this);
                this.setScreen(optionScreen);
                break;
            case LOBBY:
                if(lobbyScreen == null) lobbyScreen = new LobbyScreen(this);
                this.setScreen(lobbyScreen);
                break;
            case LOBBYSELECTION:
                if(lobbySelectionScreen == null) lobbySelectionScreen = new LobbySelectionScreen(this);
                this.setScreen(lobbySelectionScreen);
                break;
            case PLAY:
                if(playScreen == null) playScreen = new PlayScreen(this);
                this.setScreen(playScreen);
                break;
        }
    }
}

