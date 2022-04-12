package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class PreferencesScreen implements Screen {

    private final MyGame game;
    private final Stage stage;

    private final Texture background = new Texture("Backgrounds/cow-1575964.jpg");

    private Table mainTable;

    public PreferencesScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        mainTable = new Table();
        Table table = new Table();
        mainTable.setSize(stage.getWidth() / 2, stage.getHeight());
        stage.addActor(mainTable);
        mainTable.add(table);
        Table t = new Table();
        mainTable.add(t);
        t.setSize(stage.getWidth() * 0.3f, stage.getHeight());

        Skin skin = AssetsManager.getUISkin();

        //create elements
        //volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(game.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(game.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });

        // return to main screen button
        ImageButton backButton = new ImageButton(MyGame.getDrawable("text_sample/back.png"));
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.MAIN_MENU);
            }
        });

        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( game.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        Image preferences = new Image(MyGame.getDrawable("text_sample/options.png"));
        Image musicVolume = new Image(MyGame.getDrawable("text_sample/musicVolume.png"));
        Image soundVolume = new Image(MyGame.getDrawable("text_sample/soundVolume.png"));
        Image musicOnOff = new Image(MyGame.getDrawable("text_sample/music.png"));
        Image soundOnOff = new Image(MyGame.getDrawable("text_sample/sound.png"));


        table.add(preferences).colspan(2);
        table.row().pad(30, 0, 0, 10);
        table.add(musicVolume).left();
        table.add(volumeMusicSlider);
        table.row().pad(10, 0, 0, 10);
        table.add(musicOnOff);
        table.add(musicCheckbox);
        table.row().pad(10, 0, 0, 10);
        table.add(soundVolume).left();
        table.add(soundMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(soundOnOff);
        table.add(soundEffectsCheckbox);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);
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
        if(mainTable != null)
            mainTable.setSize(stage.getWidth() / 2, stage.getHeight());
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
