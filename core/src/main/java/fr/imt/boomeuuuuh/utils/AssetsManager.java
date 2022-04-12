package fr.imt.boomeuuuuh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.imt.boomeuuuuh.MyGame;

import java.util.HashMap;
import java.util.Map;

public class AssetsManager {

    private static final Map<String, Sound> SOUND_CACHE = new HashMap<>();

    private static final Map<String, Music> MUSIC_CACHE = new HashMap<>();
    private static Music MUSIC;
    private static String CURRENT_MUSIC = "";
    private static Skin UI_SKIN;

    public static void stopMusic() {
        if (MUSIC == null)
            return;

        MUSIC.stop();
        MUSIC = null;
        CURRENT_MUSIC = "";
    }

    public static void playMusic(String name, float volume) {
        if (!MyGame.getInstance().preferences.isMusicEnabled())
            return;

        String path = "Sounds/" + name + ".mp3";
        if (CURRENT_MUSIC.equalsIgnoreCase(name) && MUSIC != null)
            return;
        if (MUSIC != null)
            stopMusic();

        Music music = MUSIC_CACHE.get(name);
        if (music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal(path));
            MUSIC_CACHE.put(name, music);
        }

        music.setLooping(true);
        music.setVolume(volume);
        music.play();

        MUSIC = music;
        CURRENT_MUSIC = name;
    }

    public static void playMusic(String name) {
        playMusic(name, MyGame.getInstance().preferences.getMusicVolume());
    }

    public static void playSound(String name, float volume) {
        if (!MyGame.getInstance().preferences.isSoundEffectsEnabled())
            return;

        String path = "Sounds/" + name + ".wav";

        Sound sound = SOUND_CACHE.get(name);
        if (sound == null) {
            sound = Gdx.audio.newSound(Gdx.files.internal(path));
            SOUND_CACHE.put(name, sound);
        }

        sound.play(volume);
    }

    public static void playSound(String name) {
        playSound(name, MyGame.getInstance().preferences.getSoundVolume());
    }

    public static void changeVolume(float volume) {
        if (MUSIC == null)
            return;
        MUSIC.setVolume(volume);
    }

    public static Skin getUISkin() {
        if (UI_SKIN == null)
            UI_SKIN = new Skin(Gdx.files.internal("orange/uiskin.json"));

        return UI_SKIN;
    }
}
