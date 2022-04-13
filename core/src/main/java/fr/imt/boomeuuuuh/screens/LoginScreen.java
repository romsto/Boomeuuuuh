package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
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

    private boolean hasAcceptedHRT;
    private Table RGPDTable;
    private Cell RGPDTextCell;

    private final Texture background = new Texture("other/background.jpg");

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


        //HRT---------------
        RGPDTable = new Table();
        Label RGPDText = new Label("Reglement general sur la protection des donnees\n" +
                "\n" +
                "Les informations recueillies sur ce formulaire sont enregistrees dans un fichier informatise par l'equipe 6-bits pour la creation des comptes individuels. La base legale du traitement est le consentement.\n" +
                "Les donnees collectees seront communiquees aux seuls destinataires suivants : Le serveur que vous avez renseignees.\n" +
                "Les donnees sont conservees tant que la base de données n'est pas detruite.\n" +
                "Vous pouvez acceder aux donnees vous concernant, les rectifier, demander leur effacement ou exercer votre droit à la limitation du traitement de vos donnees.\n" +
                "Consultez le site cnil.fr pour plus d’informations sur vos droits.\n" +
                "Si vous estimez que vos droits 'Informatique et Libertes' ne sont pas respectes, vous pouvez adresser une reclamation à la CNIL.", skin, "white");
        RGPDText.setAlignment(Align.center);
        ImageButton noIDont = new ImageButton(MyGame.getDrawable("text_sample/back.png"));
        ImageButton yesIDo = new ImageButton(MyGame.getDrawable("text_sample/register.png"));

        RGPDTable.setBackground(skin.getDrawable("button-orange"));
        float mw = stage.getWidth() * 2/3;
        float mh = stage.getHeight() * 9/10;
        RGPDTable.setSize(mw, mh);
        RGPDTable.setPosition(stage.getWidth()/2, stage.getHeight()/2, Align.center);

        RGPDText.setWrap(true);
        RGPDTextCell = RGPDTable.add(RGPDText).expandX().prefWidth(mw * 0.9f);
        RGPDTable.row().pad(10, 0, 10, 0);

        Table RGPDSubTable = new Table();
        RGPDSubTable.add(noIDont);
        RGPDSubTable.add(yesIDo);
        RGPDTable.add(RGPDSubTable);

        stage.addActor(RGPDTable);
        RGPDTable.setZIndex(1);
        RGPDTable.setVisible(false);
        //------------------

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

                if(!hasAcceptedHRT){
                    RGPDTable.setVisible(true);
                    return;
                }

                MyGame.getInstance().serverConnection.send(new CreateAccountPacket(username.getText(), password.getText()));
            }
        });

        //HRT
        noIDont.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RGPDTable.setVisible(false);
            }
        });
        yesIDo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RGPDTable.setVisible(false);
                hasAcceptedHRT = true;
                MyGame.getInstance().serverConnection.send(new CreateAccountPacket(username.getText(), password.getText()));
            }
        });
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

        float mw = stage.getWidth() * 2/3;
        float mh = stage.getHeight() * 9/10;
        RGPDTable.setSize(mw, mh);
        RGPDTable.setPosition(stage.getWidth()/2, stage.getHeight()/2, Align.center);
        RGPDTextCell.prefWidth(mw * 0.9f);
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