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
import fr.imt.boomeuuuuh.PlayerData;
import fr.imt.boomeuuuuh.network.packets.client.SelectSkinPacket;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class StatsScreen implements Screen {

    private final MyGame game;
    private final Stage stage;
    private boolean changeUsed = false;

    private static final Texture background = new Texture("Backgrounds/cow-1575964.jpg");

    public StatsScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = AssetsManager.getUISkin();

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        //----------Sub table 1
        Table subTable1 = new Table();

        //----------Stats display portion
        Table statsTable = new Table();
        statsTable.setBackground(skin.getDrawable("button-orange"));
        PlayerData data = MyGame.getInstance().playerData;

        String[] labels = {"Level ", "Gold ", "Kills ", "Max streak ", "Wins "};
        int[] values = {data.level, data.gold, data.kills, data.maxkillstreak, data.wins};

        for(int i = 0; i < labels.length; i++){
            statsTable.add(new Label(labels[i], skin, "error"));
            statsTable.add(new Label(String.valueOf(values[i]), skin, "white"));
            statsTable.row();
        }

        subTable1.add(statsTable).fill();

        //----------Current skin display
        Table currSkinTable = new Table();
        currSkinTable.setBackground(skin.getDrawable("button-orange"));

        currSkinTable.add(new Label("SKIN", skin, "title-white"));
        currSkinTable.row();
        Image currSkinImg = fr.imt.boomeuuuuh.utils.Skin.getByDataName(data.currentSkin).getIcon();
        currSkinTable.add(currSkinImg).width(32*3).height(32*3);

        subTable1.add(currSkinTable).fill();

        //---------------------
        mainTable.add(subTable1).fill();
        //----------Owned skins
        Table ownedSkinTable = new Table();
        ownedSkinTable.setBackground(skin.getDrawable("button-orange"));

        ownedSkinTable.add(new Label("Owned Skins", skin, "title-white"));
        ownedSkinTable.row();

        Table ownedSkinSubTable = new Table();
        ScrollPane skins = new ScrollPane(ownedSkinSubTable, skin);
        for (final String sName : data.unlockedSkins){
            ImageButton s = new ImageButton(fr.imt.boomeuuuuh.utils.Skin.getByDataName(sName).getIcon().getDrawable());
            ownedSkinSubTable.add(s);

            if (data.currentSkin.equals(sName))
                continue;

            s.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (changeUsed) return;
                    MyGame.getInstance().serverConnection.send(new SelectSkinPacket(sName));
                    changeUsed = true;
                }
            });
        }
        ownedSkinTable.add(skins);

        mainTable.row();
        mainTable.add(ownedSkinTable).fill();

        //Shop
        TextButton shopButton = new TextButton("SHOP", skin);
        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.SKINS);
            }
        });

        mainTable.row().pad(10,0,10,0);
        mainTable.add(shopButton);

        stage.addActor(mainTable);

        // return to main screen button
        ImageButton backButton = new ImageButton(MyGame.getDrawable("text_sample/back.png"));
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen((MyGame.getInstance().hasLobby())? ScreenType.LOBBY : ScreenType.LOBBY_SELECTION);
            }
        });
        stage.addActor(backButton);
    }

    public void reShow(){
        changeUsed = false;
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

    }
}
