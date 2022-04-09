package fr.imt.boomeuuuuh;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.imt.boomeuuuuh.network.packets.both.TestPacket;

public class HUD {

    private final BitmapFont font = new BitmapFont();

    private final Texture[] imgs;

    private int displayWidth;
    private int displayHeight;
    private float fontHeight;

    private final int height = 40;
    private final int width = 40;
    private final int[] ys;
    private final int xImg = 0;
    private final int xTxt = width + 4;

    public HUD(int displayWidth, int displayHeight){

        ys = new int[4];
        computePos(displayWidth, displayHeight);

        //Load textures
        Texture bomb = new Texture("HUD/bomb.png");
        Texture power = new Texture("HUD/power.png");
        Texture speed = new Texture("HUD/speed.png");
        Texture kills = new Texture("HUD/vache_m.png");

        imgs = new Texture[] {bomb, power, speed, kills};
    }

    public void computePos(int dispWidth, int dispHeight){
        displayWidth = dispWidth; displayHeight = dispHeight;

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "0000");
        fontHeight = glyphLayout.height;

        for (int i = 0; i < ys.length; i++)
            ys[i] = displayHeight - (height + 5) * (i + 1);
    }

    public void drawHUD(SpriteBatch batch){
        Game game = Game.getInstance();
        int[] vs = {game.player_bomb, game.player_bomb_power, game.player_speed, game.player_kills};

        font.setColor(1f, 1f, 1f, 0.9f);
        for (int i = 0; i < vs.length; i++){
        batch.draw(imgs[i], xImg, ys[i], width, height);
        drawText(batch, String.valueOf(vs[i]), xTxt, ys[i] + height/2);
        }

        if (game.player == null) {
            font.setColor(1f, 0, 0, 1f);
            drawText(batch, "You're dead :c", 0, ys[ys.length - 1] - height);
        }
    }

    private void drawText(SpriteBatch batch, String txt, int x, int y) {
        font.draw(batch, txt, x, y + fontHeight / 2);
    }

    private void drawTextCentered(SpriteBatch batch, String txt, int x, int y) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, txt);
        float fontHeight = glyphLayout.height;
        float fontWidth = glyphLayout.width;

        font.draw(batch, txt, x + fontWidth / 2, y + fontHeight / 2);
    }
}
