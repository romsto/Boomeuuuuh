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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.client.CreateLobbyPacket;
import fr.imt.boomeuuuuh.network.packets.client.RequestLobbyListPacket;
import fr.imt.boomeuuuuh.utils.AssetsManager;
import fr.imt.boomeuuuuh.utils.LobbyInfoList;

import java.util.ArrayList;
import java.util.List;

public class LobbySelectionScreen implements Screen {


    private final MyGame game; // Note it's "MyGame" not "Game"
    private final Stage stage;
    public Label messageLabel;
    public List<LobbyInfoList> lobbies = new ArrayList<>();
    public Table scrollTable;

    private final Texture background = new Texture("other/background.jpg");

    // constructor to keep a reference to the main Game class
    public LobbySelectionScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Perfect background
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {

        Skin skin = AssetsManager.getUISkin();

        // called when this screen is set as the screen with game.setScreen();
        final Image titleImg = new Image(MyGame.getDrawable("text_sample/lobby_selection.png"));
        final ImageButton backButton = new ImageButton(MyGame.getDrawable("text_sample/back.png"));
        final ImageButton refreshButton = new ImageButton(MyGame.getDrawable("text_sample/refresh.png"));
        final ImageButton createButton = new ImageButton(MyGame.getDrawable("text_sample/create.png"));
        final TextButton statsButton = new TextButton("Stats", skin);

        messageLabel = new Label("", skin);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        mainTable.add(titleImg);
        mainTable.row().pad(10, 0, 10, 0);

        scrollTable = new Table();
        //scrollTable.setFillParent(true);
        //stage.addActor(scrollTable);
        mainTable.add(scrollTable);
        mainTable.row().pad(10,0,10,0);

        ScrollPane lobbyScroll = new ScrollPane(null, skin);

        ScrollPane.ScrollPaneStyle lobbyScrollStyle = new ScrollPane.ScrollPaneStyle();
        lobbyScroll.setActor(scrollTable);

        Table table = new Table(skin);
        //table.setFillParent(true);
        //stage.addActor(table);
        mainTable.add(table);

        table.add(messageLabel).fillX().uniformX().colspan(2);
        table.row().pad(10, 10, 10, 10);
        table.add(lobbyScroll).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(refreshButton).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(createButton).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(statsButton).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(backButton).fillX().uniformX();

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MyGame.getInstance().connected = false;
                MyGame.getInstance().username = null;
                MyGame.getInstance().logged = false;
                MyGame.getInstance().serverConnection = null;
                game.changeScreen(ScreenType.MAIN_MENU);
            }
        });
        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scrollTable.clear();
                if (game.connected) game.serverConnection.send(new RequestLobbyListPacket());
            }
        });
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.connected) game.serverConnection.send(new CreateLobbyPacket(game.username));
            }
        });
        statsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.STATS);
            }
        });
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
