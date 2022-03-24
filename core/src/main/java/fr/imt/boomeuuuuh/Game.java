package fr.imt.boomeuuuuh;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.imt.boomeuuuuh.entities.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static List<Entity> entities = new ArrayList<>();
    public int sizeX;
    public int sizeY;
    public int nbrJoueur;
    public TiledMap map;
    public  OrthogonalTiledMapRenderer renderer;
    public SpriteBatch batch;
    private OrthographicCamera camera;



    public Game() {
        loadMap();
        batch = new SpriteBatch();
    }
    public void draw(float temps){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        //for (Entity entity : entities) {
        //    entity.draw(batch, temps);
        //}
    }
    public void loadMap(){
        map = new TmxMapLoader().load("map/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(480,480);
        camera.position.x = 240;
        camera.position.y = 240;
        Viewport viewport = new StretchViewport(480,480);

    }

    public void dispose () {
        map.dispose();
        renderer.dispose();
    }


}
