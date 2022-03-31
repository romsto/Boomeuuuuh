package fr.imt.boomeuuuuh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.HardBlock;
import fr.imt.boomeuuuuh.entities.Player;
import fr.imt.boomeuuuuh.utils.Location;

import java.util.ArrayList;
import java.util.List;

public class Game implements InputProcessor {

    private static Game instance;

    private final List<Entity> entities;
    private final List<Entity> toBeRemovedEntities;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private final Player player;

    public Game() {
        loadMap();
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(), false);

        entities = new ArrayList<>();
        toBeRemovedEntities = new ArrayList<>();

        BodyDef borders = new BodyDef();
        borders.position.set(new Vector2());

        Body bordersBody = world.createBody(borders);
        EdgeShape edgeShape = new EdgeShape();
        FixtureDef fixtureBorders = new FixtureDef();
        fixtureBorders.filter.categoryBits = Entity.SOLID_CATEGORY;
        edgeShape.set(new Vector2(32 / 100f, 32 / 100f), new Vector2(14 * 32 / 100f, 32 / 100f));
        fixtureBorders.shape = edgeShape;
        bordersBody.createFixture(fixtureBorders);
        edgeShape.set(new Vector2(14 * 32 / 100f, 32 / 100f), new Vector2(14 * 32 / 100f, 14 * 32 / 100f));
        fixtureBorders.shape = edgeShape;
        bordersBody.createFixture(fixtureBorders);
        edgeShape.set(new Vector2(32 / 100f, 32 / 100f), new Vector2(32 / 100f, 14 * 32 / 100f));
        fixtureBorders.shape = edgeShape;
        bordersBody.createFixture(fixtureBorders);
        edgeShape.set(new Vector2(32 / 100f, 14 * 32 / 100f), new Vector2(14 * 32 / 100f, 14 * 32 / 100f));
        fixtureBorders.shape = edgeShape;
        bordersBody.createFixture(fixtureBorders);

        edgeShape.dispose();

        spawnEntity(new HardBlock(0, new Location(2, 2), world));
        spawnEntity(new HardBlock(1, new Location(2, 4), world));
        spawnEntity(new HardBlock(2, new Location(2, 5), world));

        player = new Player(3, new Location(1, 1), world);
        spawnEntity(player);
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
        handleMovements();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Matrix4 debugMatrix = batch.getProjectionMatrix().cpy().scale(100,
                100, 0);

        world.step(delta, 8, 3);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        renderer.render();
        debugRenderer.render(world, debugMatrix);

        batch.begin();
        for (Entity entity : entities) {
            entity.draw(batch, delta);
        }
        batch.end();

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

    public void handleMovements() {
        boolean left = Gdx.input.isKeyPressed(Input.Keys.Q);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.Z);

        player.getBody().setLinearVelocity(new Vector2());
        player.getBody().applyLinearImpulse(new Vector2((left ? -1f : 0f) + (right ? 1f : 0f), (down ? -1f : 0f) + (up ? 1f : 0f)), player.getBody().getWorldCenter(), false);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case (Input.Keys.SPACE):
                // TODO Place a bomb
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}