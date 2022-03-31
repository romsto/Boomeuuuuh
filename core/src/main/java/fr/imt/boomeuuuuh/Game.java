package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import fr.imt.boomeuuuuh.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game instance;

    private final List<Entity> entities;
    private final SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public Game() {
        loadMap();
        batch = new SpriteBatch();

        entities = new ArrayList<>();
    }

    /**
     * Gets the Singleton Instance
     *
     * @return Game
     */
    public static Game getInstance() {
        return instance;
    }

    public void draw(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        for (Entity entity : entities) {
            entity.draw(batch, delta);
        }
    }

    public void loadMap() {
        map = new TmxMapLoader().load("map/map1Final.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(480, 480);
        camera.position.x = 240;
        camera.position.y = 240;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public void spawnEntity(Entity entity) {
        entities.add(entity);
        // TODO Create hitbox to make the entity react
    }

    public void removeEntity(Entity entity) {
        // TODO Remove hitbox and kill entity
        entities.remove(entity);
    }

    public Entity getEntity(int id) {
        return entities.get(id);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
