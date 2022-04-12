package fr.imt.boomeuuuuh.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fr.imt.boomeuuuuh.MyGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.addIcon("logos/logo128.png", Files.FileType.Internal);
        config.addIcon("logos/logo32.png", Files.FileType.Internal);
        config.addIcon("logos/logo16.png", Files.FileType.Internal);

        config.title = "BOOMEUUUUH";

        new LwjglApplication(new MyGame(), config);
    }
}

