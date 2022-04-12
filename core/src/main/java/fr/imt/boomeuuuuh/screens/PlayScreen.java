package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;

public class PlayScreen implements Screen {


    private final Game game; // Note it's "MyGame" not "Game"

    // constructor to keep a reference to the main Game class
    public PlayScreen(MyGame game) {
        this.game = Game.getInstance();
        Gdx.input.setInputProcessor(this.game);
    }

    @Override
    public void render(float delta) {
        game.draw(delta);
        /* Make all the possibilities for the screen to lead to another one
        if (Gdx.input.justTouched()) // Criterion
            game.setScreen(game.anotherScreen);  // Screen to change
         */
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
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
        game.dispose();
    }

}
