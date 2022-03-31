package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Game instance;

    private final List<Entity> entities;
    private final List<Entity> toBeRemovedEntities;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public Game() {
        loadMap();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(), false);

        entities = new ArrayList<>();
        toBeRemovedEntities = new ArrayList<>();
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

        Matrix4 debugMatrix = batch.getProjectionMatrix().cpy().scale(100,
                100, 0);

        camera.update();
        renderer.setView(camera);
        renderer.render();
        debugRenderer.render(world, debugMatrix);

        for (Entity entity : entities) {
            entity.draw(batch, delta);
        }

        removeEntities();
    }

    public void loadMap() {
        map = new TmxMapLoader().load("map/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera(480, 480);
        camera.setToOrtho(false);
        camera.update();
        camera.position.x = 240;
        camera.position.y = 240;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public void spawnEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        toBeRemovedEntities.add(entity);
    }

    private void removeEntities() {
        for (Entity entity : toBeRemovedEntities) {
            world.destroyBody(entity.getBody());
            entity.dispose();
            entities.remove(entity);
        }
        toBeRemovedEntities.clear();
    }

    public Entity getEntity(int id) {
        return entities.get(id);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}