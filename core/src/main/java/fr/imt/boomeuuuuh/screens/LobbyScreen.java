package fr.imt.boomeuuuuh.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.client.LeavePacket;

public class LobbyScreen implements Screen {


    private final MyGame game;
    private final Stage stage;
    private Label lobbyname;
    private Label players;
    private Label owner;
    private Lobby lobby;
    // private BombeStandard st;
    //private BombeStandard st1;
    // public SpriteBatch batch;
    // public float   temps;

    public LobbyScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        //batch= new SpriteBatch();
        //temps = 0.0F;
    }

    @Override
    public void show() {
        lobby = MyGame.getInstance().lobby;
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        //create items
        lobbyname = new Label("In Lobby " + lobby.name, skin);
        players = new Label("Current players : " + lobby.players, skin);
        owner = new Label(lobby.isOwner ? "You are owner of the lobby" : " ", skin);
        TextButton start = new TextButton("Start the game", skin);
        TextButton leave = new TextButton("Leave the game", skin);

        //add buttons to table
        table.add(lobbyname).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(players).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(owner).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(start).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(leave).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        leave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.serverConnection.send(new LeavePacket());
                game.changeScreen(ScreenType.LOBBY_SELECTION);

                Lobby lobby = game.lobby;
                if (lobby == null)
                    return;
                lobby.lobbyConnection.close();
                if (lobby.game != null)
                    lobby.game.dispose();
                lobby.game = null;

                game.lobby = null;
            }
        });
    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        lobbyname.setText("In Lobby " + lobby.name);
        players.setText("Current players : " + lobby.players);
        owner.setText(lobby.isOwner ? "You are owner of the lobby" : " ");
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}
