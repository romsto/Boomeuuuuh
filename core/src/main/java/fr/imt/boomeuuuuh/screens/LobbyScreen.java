package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.client.LaunchGamePacket;
import fr.imt.boomeuuuuh.network.packets.client.LeavePacket;
import fr.imt.boomeuuuuh.network.packets.client.SendChatPacket;
import sun.tools.jconsole.Tab;

public class LobbyScreen implements Screen {


    private final MyGame game;
    private final Stage stage;
    private Label lobbyname;
    private Label players;
    private Label owner;
    public Label info;
    private Lobby lobby;
    // private BombeStandard st;
    //private BombeStandard st1;
    // public SpriteBatch batch;
    // public float   temps;

    //Chat area
    private Label chat_label;
    private ScrollPane chat_scroll;

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
        info = new Label("", skin);
        TextButton start = new TextButton("Start the game", skin);
        TextButton leave = new TextButton("Leave the game", skin);

        //Create tables
        Table infoTable = new Table();
        Table actionsTable = new Table();
        Table chatTable = new Table();
        Table subChatTable = new Table();
        Table buttonsTable = new Table();

        table.add(infoTable).fill();
        table.row();
        table.add(actionsTable).fill();

        actionsTable.add(chatTable).fill().uniform().height(200);
        actionsTable.add(buttonsTable).fill().uniform();

        //add buttons to table
        //---------INFO TABLE---------
        infoTable.add(lobbyname).fillX().uniformX();
        infoTable.row().pad(10, 0, 10, 0);
        infoTable.add(players).fillX().uniformX();
        infoTable.row().pad(5, 0, 5, 0);
        infoTable.add(owner).fillX().uniformX();
        //----------------------------
        //--------ACTION TABLE--------
        buttonsTable.add(info).fillX().uniformX();
        buttonsTable.row().pad(10, 0, 10, 0);
        buttonsTable.add(start).fillX().uniformX();
        buttonsTable.row().pad(10, 0, 10, 0);
        buttonsTable.add(leave).fillX().uniformX();

        //---------CHAT AREA
        chatTable.bottom();

        chat_label = new Label("START OF CHAT \n", skin);
        chat_label.setWrap(true);
        chat_label.setAlignment(Align.bottomLeft);

        chat_scroll = new ScrollPane(chat_label, skin);
        chat_scroll.setFadeScrollBars(false);

        chatTable.add(chat_scroll).fill();
        chatTable.row();
        chatTable.add(subChatTable).fillX();

        final TextArea chat_input = new TextArea("", skin);
        ScrollPane chat_input_scroll = new ScrollPane(chat_input, skin);
        chat_input_scroll.setFadeScrollBars(false);
        TextButton sendChatB = new TextButton("Send", skin);

        subChatTable.add(chat_input_scroll).fill();
        subChatTable.add(sendChatB);
        //------------------
        //----------------------------

        sendChatB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SendChatPacket p = new SendChatPacket(chat_input.getText());
                MyGame.getInstance().serverConnection.send(p);
            }
        });

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.serverConnection.send(new LaunchGamePacket());
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

        //Check for messages
        String a = lobby.collectChat();
        chat_label.setText( chat_label.getText() + a);
        if (a.length() != 0)
            chat_scroll.scrollTo(0,0,0,0);
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
