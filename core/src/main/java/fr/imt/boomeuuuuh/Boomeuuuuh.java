package fr.imt.boomeuuuuh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

public class Boomeuuuuh extends ApplicationAdapter {
	SpriteBatch batch;
	private  float   temps;

	@Override
	public void create () {
		batch = new SpriteBatch();
		temps= 0.0F;
	}

	@Override
	public void render () {
		temps += Gdx.graphics.getDeltaTime();
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		//Game.draw(batch,temps);//
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
