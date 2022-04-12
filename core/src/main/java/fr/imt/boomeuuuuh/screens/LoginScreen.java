package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.common.base.CharMatcher;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.client.CreateAccountPacket;
import fr.imt.boomeuuuuh.network.packets.client.LogInPacket;
import fr.imt.boomeuuuuh.utils.AssetsManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen implements Screen {

    private static final Pattern illegalChars = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^ ]");

    private final MyGame game;
    private final Stage stage;
    public Label label;

    public LoginScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = AssetsManager.getUISkin();

        //create elements
        Image connectionPageImage = new Image(MyGame.getDrawable("text_sample/connection_page.png"));
        label = new Label("", skin);
        final TextField username = new TextField("Username", skin);
        final TextField password = new TextField("Password", skin);
        ImageButton login = new ImageButton(MyGame.getDrawable("text_sample/login.png"));
        ImageButton register = new ImageButton(MyGame.getDrawable("text_sample/register.png"));
        final ImageButton backButton = new ImageButton(MyGame.getDrawable("text_sample/back.png")); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version


        //add buttons to table
        table.add(connectionPageImage).fillX().uniform();
        table.row().pad(10, 0, 10, 0);
        table.add(label).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(username).fillX().uniformX();
        table.row();
        password.setPasswordMode(true);
        table.add(password).fillX().uniformX();
        table.row();
        table.add(login).fillX().uniformX();
        table.row();
        table.add(register).fillX().uniformX();
        table.row();
        table.add(backButton).fillX().uniformX();

        // create button listeners
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.MAIN_MENU);
            }
        });

        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = username.getText();
                String passwordText = password.getText();
                if (text == null || passwordText == null)
                    return;
                if (text.length() <= 3 || passwordText.length() <= 3) {
                    label.setText("Your username or password is too short...");
                    return;
                }
                if (containsIllegalChars(text) || containsIllegalChars(passwordText)) {
                    label.setText("Your username or password contains invalid characters...");
                    return;
                }

                if (!game.connected) {
                    label.setText("You are not connected to the server. Please try again later.");
                    return;
                }

                MyGame.getInstance().serverConnection.send(new LogInPacket(username.getText(), password.getText()));
            }
        });

        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = username.getText();
                String passwordText = password.getText();
                if (text == null || passwordText == null)
                    return;
                if (text.length() <= 3 || passwordText.length() <= 3) {
                    label.setText("Your username or password is too short...");
                    return;
                }
                if (containsIllegalChars(text) || containsIllegalChars(passwordText)) {
                    label.setText("Your username or password contains invalid characters...");
                    return;
                }

                if (!game.connected) {
                    label.setText("You are not connected to the server. Please try again later.");
                    return;
                }

                MyGame.getInstance().serverConnection.send(new CreateAccountPacket(username.getText(), password.getText()));
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

    private boolean containsIllegalChars(String str) {
        Matcher matcher = illegalChars.matcher(str);
        return matcher.find() || !CharMatcher.ascii().matchesAllOf(str);
    }

}