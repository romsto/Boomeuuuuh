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
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.PlayerData;
import fr.imt.boomeuuuuh.network.packets.client.SelectSkinPacket;
import fr.imt.boomeuuuuh.network.packets.client.UnlockSkinPacket;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class SkinsScreen implements Screen {

    private final MyGame game;
    private final Stage stage;
    private Label skinLabel;
    private ScrollPane skinScroll;
    public Table skinTable;
    public Label gold;
    public boolean act = true;

    private ImageButton lobbyButton;

    private static final Texture background = new Texture("Backgrounds/cow-1575964.jpg");

    public SkinsScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = AssetsManager.getUISkin();

        Table table = new Table();
        table.setFillParent(true);

        gold = new Label(MyGame.getInstance().playerData.gold + " gold", skin);
        table.add(gold);

        skinLabel = new Label("", skin);
        skinLabel.setWrap(true);
        skinLabel.setAlignment(Align.center);

        skinScroll = new ScrollPane(skinLabel, skin);
        skinScroll.setFadeScrollBars(false);

        table.add(skinScroll).fill();
        table.row();

        skinTable = new Table();
        table.add(skinTable).fill();
        table.row();

        PlayerData playerData = MyGame.getInstance().playerData;

        for (final fr.imt.boomeuuuuh.utils.Skin value : fr.imt.boomeuuuuh.utils.Skin.values()) {
            skinTable.row().pad(10, 0, 10, 0);
            boolean hasSkin = playerData.hasSkin(value);
            TextButton button;
            if (hasSkin) {
                if (playerData.getCurrentSkin() == value) {
                    button = new TextButton("Current Skin", skin, "maroon");
                } else {
                    button = new TextButton("Select Skin", skin);
                    button.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            if (!act) return;
                            MyGame.getInstance().serverConnection.send(new SelectSkinPacket(value.getDataName()));
                            act = false;
                        }
                    });
                }
            } else {
                if (playerData.gold >= 100) {
                    button = new TextButton("100 gold", skin);
                    button.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            if (!act) return;
                            MyGame.getInstance().serverConnection.send(new UnlockSkinPacket(value.getDataName()));
                            act = false;
                        }
                    });
                } else {
                    button = new TextButton("Not enough gold", skin);
                }
            }

            skinTable.add(value.getIcon()).width(32).height(32);
            skinTable.add(button).fillX().uniformX();
        }

        stage.addActor(table);

        // return to main screen button
        ImageButton backButton = new ImageButton(MyGame.getDrawable("text_sample/back.png"));
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.STATS);
            }
        });
        stage.addActor(backButton);


        lobbyButton = new ImageButton(MyGame.getDrawable((MyGame.getInstance().hasLobby())? "text_sample/lobby.png" : "text_sample/lobby_selection.png"));
        lobbyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen((MyGame.getInstance().hasLobby())? ScreenType.LOBBY : ScreenType.LOBBY_SELECTION);
            }
        });
        lobbyButton.right();
        lobbyButton.setPosition(stage.getWidth() - lobbyButton.getWidth(), 0);
        stage.addActor(lobbyButton);
    }

    public void reShow(){
        act = true;
        stage.clear();
        show();
    }


    @Override
    public void render(float delta) {
        AssetsManager.playMusic("menu");
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Perfect background
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();

        //Put the correct position for the button
        lobbyButton.setPosition(stage.getWidth() - lobbyButton.getWidth(), 0);

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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}