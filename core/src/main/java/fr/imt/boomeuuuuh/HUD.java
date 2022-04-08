package fr.imt.boomeuuuuh;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {

    private final BitmapFont font = new BitmapFont();

    private final Texture bomb;
    private final Texture power;
    private final Texture speed;
    private final Texture kills;

    public HUD(){
        //Load textures
        bomb = new Texture("HUD/bomb.png");
        power = new Texture("HUD/power.png");
        speed = new Texture("HUD/speed.png");
        kills = new Texture("HUD/vache_m.png");
    }

    public void drawHUD(SpriteBatch batch, int displayWidth, int displayHeight){
        Game game = Game.getInstance();

        int height = 40;
        int y = displayHeight - height - 5;
        int x = 0;

        int width = height;
        font.setColor(1f, 1f, 1f, 0.9f);
        batch.draw(bomb, x, y, width, height);
        drawText(batch, String.valueOf(game.player_bomb), x + width + 4, y + height/2);
        y -= height + 5;

        batch.draw(power, x, y, width, height);
        drawText(batch, String.valueOf(game.player_bomb_power), x + width + 4, y + height / 2);
        y -= height + 5;

        batch.draw(speed, x, y, width, height);
        drawText(batch, String.valueOf(game.player_speed), x + width + 4, y + height / 2);
        y -= height + 5;

        batch.draw(kills, x, y, width, height);
        drawText(batch, String.valueOf(game.player_kills), x + width + 4, y + height / 2);

        if (game.player == null) {
            font.setColor(1f, 0, 0, 1f);
            drawTextCentered(batch, "You're dead :c", displayWidth / 2, displayHeight / 2);
        }
    }

    private int drawText(SpriteBatch batch, String txt, int x, int y) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, txt);
        float fontHeight = glyphLayout.height;

        font.draw(batch, txt, x, y + fontHeight / 2);
        return ((int) fontHeight);
    }

    private void drawTextCentered(SpriteBatch batch, String txt, int x, int y) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, txt);
        float fontHeight = glyphLayout.height;
        float fontWidth = glyphLayout.width;

        font.draw(batch, txt, x + fontWidth / 2, y + fontHeight / 2);
    }
}
