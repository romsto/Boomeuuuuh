package fr.imt.boomeuuuuh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum Skin {

    KAREDAS(1),
    APOCALISTE(2),
    ATLANTAS(3),
    EMAZTEK(4);

    private final int number;

    Skin(int number) {
        this.number = number;
    }

    public static Skin getByDataName(String dataName) {
        return values()[Integer.parseInt(dataName.replaceAll("skin", "")) - 1];
    }

    public int getNumber() {
        return number;
    }

    public String getDataName() {
        return "skin" + number;
    }

    public Image getIcon() {
        return new Image(TextureRegion.split(new Texture(Gdx.files.internal("skin/skinDown" + number + ".png")), 32, 32)[0][0]);
    }
}
