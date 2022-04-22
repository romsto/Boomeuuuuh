package fr.imt.boomeuuuuh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import sun.tools.jconsole.Tab;

public enum BoomLetter {

    HOLD("HOLD");

    private final String name;

    BoomLetter(String name){this.name = name;}

    public static Table makeWord(String word){
        Table wordTable = new Table();
        for (String l : word.split(""))
            wordTable.add(getBoomType(l).getImage()).size(32, 32);

        return wordTable;
    }

    public Image getImage() {
        return new Image(new Texture("BoomLetters/" + name + ".png"));
    }

    public static BoomLetter getBoomType(String l){
        l = l.toUpperCase();
        for (BoomLetter b : values())
            if( l.equals(b.name))
                return b;
        return HOLD; //Change to a question mark or a cow head
    }
}
