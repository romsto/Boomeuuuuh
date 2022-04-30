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

package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.utils.AssetsManager;

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
        AssetsManager.playMusic("battle");
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
