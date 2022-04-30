/*
 * Copyright (c) 2022.
 * Authors : Storaï R, Faure B, Mathieu A, Garry A, Nicolau T, Bregier M.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

    /**
     * Stop the music if it is running
     */
    public static void stopMusic() {
        if (MUSIC == null)
            return;

        MUSIC.stop();
        MUSIC = null;
        CURRENT_MUSIC = "";
    }

    /**
     * Play a music at a specific volume. If a music is already running it stops it or do nothing if it's the same music.
     *
     * @param name   to play
     * @param volume to play
     */
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

    /**
     * Play a music at the default volume. If a music is already running it stops it or do nothing if it's the same music.
     *
     * @param name to play
     */
    public static void playMusic(String name) {
        playMusic(name, MyGame.getInstance().preferences.getMusicVolume());
    }

    /**
     * Play a sound at a specific volume
     *
     * @param name   to play
     * @param volume to play
     */
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

    /**
     * Play a sound at the default volume
     *
     * @param name to play
     */
    public static void playSound(String name) {
        playSound(name, MyGame.getInstance().preferences.getSoundVolume());
    }

    /**
     * Change the volume of the current music if there is one
     *
     * @param volume to change
     */
    public static void changeVolume(float volume) {
        if (MUSIC == null)
            return;
        MUSIC.setVolume(volume);
    }

    /**
     * Get the skin of the UI
     *
     * @return Skin
     */
    public static Skin getUISkin() {
        if (UI_SKIN == null)
            UI_SKIN = new Skin(Gdx.files.internal("orange/uiskin.json"));

        return UI_SKIN;
    }
}
