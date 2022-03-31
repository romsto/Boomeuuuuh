package fr.imt.boomeuuuuh.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;

import javax.swing.border.TitledBorder;
import java.util.ArrayList;

public class LobbySelectionScreen implements Screen {


    private MyGame game; // Note it's "MyGame" not "Game"
    private Stage stage;
    private Label titleLabel;

    // constructor to keep a reference to the main Game class
    public LobbySelectionScreen(MyGame game){
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

        titleLabel = new Label("Lobby Selection",skin);

        // called when this screen is set as the screen with game.setScreen();
        final TextButton backButton = new TextButton("Back", skin);


        Table scrollTable = new Table();
        scrollTable.setFillParent(true);
        scrollTable.setDebug(true);
        stage.addActor(scrollTable);

        //for(get the lobby List){
        for (int i = 0; i<5;i++){
            scrollTable.row().pad(10, 0, 10, 0);
            final TextButton button = new TextButton("Number" + i, skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // do stuff
                }});
            scrollTable.add(button).fillX().uniformX();
            // add a picture
            /*
            if (the lobby is public){
                Image img = new Image(new Texture("Lobby/open.png"));}
            else(the lobby is not public){
                Image img = new Image(new Texture("Lobby/locked.png"));}
            */
            Image img = new Image(new Texture("Lobby/open.png"));
            scrollTable.add(img).width(32).height(40);
            // finish a row
        }
        ScrollPane lobbyScroll = new ScrollPane(null,skin);

        ScrollPane.ScrollPaneStyle lobbyScrollStyle = new ScrollPane.ScrollPaneStyle();
        lobbyScroll.setActor(scrollTable);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        table.add(titleLabel).fillX().uniformX().colspan(2);
        table.row().pad(10,10,10,10);
        table.add(lobbyScroll).fillX().uniformX();
        table.row().pad(10,10,10,10);
        table.add(backButton).fillX().uniformX();

        stage.addActor(table);

        /*
        ArrayList<String> lobbyArray = new ArrayList<String>();
        for (int i = 0; i<5;i++){
            lobbyArray.add("Lobby " + i);
        }
        lobbyList.item
        */

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.LOG_IN);
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
