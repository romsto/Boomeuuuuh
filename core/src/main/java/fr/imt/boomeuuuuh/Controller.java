package fr.imt.boomeuuuuh;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class Controller implements InputProcessor {

    public static boolean LEFT = false,
            RIGHT = false,
            UP = false,
            DOWN = false;

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                LEFT = true;
                break;
            case Keys.RIGHT:
                RIGHT = true;
                break;
            case Keys.DOWN:
                DOWN = true;
                break;
            case Keys.UP:
                UP = true;
                break;
            case Keys.SPACE:
                // TODO place a bomb
                break;
            case Keys.ESCAPE:
                // TODO leave
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                LEFT = false;
                break;
            case Keys.RIGHT:
                RIGHT = false;
                break;
            case Keys.DOWN:
                DOWN = false;
                break;
            case Keys.UP:
                UP = false;
                break;
            default:
                break;
        }
        return true;
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
