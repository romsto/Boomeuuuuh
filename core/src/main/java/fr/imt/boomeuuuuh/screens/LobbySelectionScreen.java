package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.ServerConnection;
import fr.imt.boomeuuuuh.network.packets.client.CreateLobbyPacket;
import fr.imt.boomeuuuuh.network.packets.client.RequestLobbyListPacket;
import fr.imt.boomeuuuuh.utils.LobbyInfoList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LobbySelectionScreen implements Screen {


    private final MyGame game; // Note it's "MyGame" not "Game"
    private final Stage stage;
    public Label titleLabel;
    public List<LobbyInfoList> lobbies = new ArrayList<>();
    public Table scrollTable;

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

        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        titleLabel = new Label("Lobby Selection", skin);

        // called when this screen is set as the screen with game.setScreen();
        final TextButton backButton = new TextButton("Disconnect", skin);
        final TextButton refreshButton = new TextButton("Refresh the list", skin);
        final TextButton createButton = new TextButton("Create a lobby", skin);


        scrollTable = new Table();
        scrollTable.setFillParent(true);
        scrollTable.setDebug(true);
        stage.addActor(scrollTable);

        ScrollPane lobbyScroll = new ScrollPane(null, skin);

        ScrollPane.ScrollPaneStyle lobbyScrollStyle = new ScrollPane.ScrollPaneStyle();
        lobbyScroll.setActor(scrollTable);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        table.add(titleLabel).fillX().uniformX().colspan(2);
        table.row().pad(10, 10, 10, 10);
        table.add(lobbyScroll).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(refreshButton).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(createButton).fillX().uniformX();
        table.row().pad(10, 10, 10, 10);
        table.add(backButton).fillX().uniformX();

        stage.addActor(table);

        if (game.connected) {
            game.serverConnection.send(new RequestLobbyListPacket());
        } else {
            try {
                game.serverConnection = new ServerConnection(MyGame.SERVER_ADDRESS, MyGame.SERVER_PORT_TCP);
                game.connected = true;
                game.serverConnection.send(new RequestLobbyListPacket());
            } catch (IOException e) {
                game.connected = false;
            }
        }

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.connected) game.serverConnection.send(new RequestLobbyListPacket());
            }
        });
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.connected) game.serverConnection.send(new CreateLobbyPacket(game.username));
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
