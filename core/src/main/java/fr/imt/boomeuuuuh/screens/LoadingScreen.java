package fr.imt.boomeuuuuh.screens;
import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.MyGame;

public class LoadingScreen implements Screen {


    private final MyGame game; // Note it's "MyGame" not "Game"

    // constructor to keep a reference to the main Game class
    public LoadingScreen(MyGame game){
        this.game = game;
    }

    @Override
    public void render(float delta) {
        game.changeScreen(ScreenType.MAIN_MENU);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
    }

    @Override
    public void hide() {
        // called when current screen changes from this to a different screen
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // never called automatically
    }
}
