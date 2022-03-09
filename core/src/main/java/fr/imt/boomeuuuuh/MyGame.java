package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Game;
import fr.imt.boomeuuuuh.screens.*;

public class MyGame extends Game {

    MainMenuScreen mainMenuScreen;
    LobbyScreen lobbyScreen;
    LoadingScreen loadingScreen;
    PlayScreen playScreen;
    OptionScreen optionScreen;
    PersonalScreen personalScreen;
    LobbySelectionScreen lobbySelectionScreen;


    @Override
    public void create() {
        mainMenuScreen = new MainMenuScreen(this);
        lobbyScreen = new LobbyScreen(this);
        lobbySelectionScreen = new LobbySelectionScreen(this);
        loadingScreen = new LoadingScreen(this);
        playScreen = new PlayScreen(this);
        optionScreen = new OptionScreen(this);
        personalScreen = new PersonalScreen(this);

        setScreen(mainMenuScreen);
    }
}

