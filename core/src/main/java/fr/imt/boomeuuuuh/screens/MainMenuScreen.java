package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class MainMenuScreen implements Screen {

    private final MyGame game;
    private final Stage stage;
    // private BombeStandard st;
    //private BombeStandard st1;
    // public SpriteBatch batch;
    // public float   temps;

    private final Texture background = new Texture("other/background.jpg");

    public MainMenuScreen(MyGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        //batch= new SpriteBatch();
        //temps = 0.0F;
    }

    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        Table subTable1 = new Table();
        //table.setFillParent(true);
        stage.addActor(table);
        //table.setSize(stage.getWidth() / 2, stage.getHeight());
        table.setFillParent(true);
        table.add(subTable1);
        subTable1.setSize(stage.getWidth() / 4, stage.getHeight());

        //create buttons
        ImageButton newGame = new ImageButton(MyGame.getDrawable("text_sample/play.png"));
        ImageButton preferences = new ImageButton(MyGame.getDrawable("text_sample/options.png"));
        ImageButton exit = new ImageButton(MyGame.getDrawable("text_sample/exit.png"));

        //add buttons to table
        subTable1.add(newGame).fillX().uniformX();
        subTable1.row().pad(10, 0, 10, 0);
        subTable1.add(exit).fillX().uniformX();

        stage.addActor(preferences);
        //st = new BombeStandard(50);
        //st1 = new BombeStandard(51);
        //st.setX_screen(150);st.setY_screen(150);st.setPower(6);
        //st.setX_screen(250);st.setY_screen(150);st.setPower(6);

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.connected)
                    game.changeScreen(ScreenType.LOG_IN);
                else
                    game.changeScreen(ScreenType.CONNECT);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(ScreenType.PREFERENCES);
            }
        });
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

        //temps += Gdx.graphics.getDeltaTime();
        // batch.begin();
        // st.draw(batch,temps );
        // st1.draw(batch,temps );
        //Game.draw(batch,temps);//
        // batch.end();

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