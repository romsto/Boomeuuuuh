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

package fr.imt.boomeuuuuh.screens;

import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.MyGame;

import java.lang.reflect.InvocationTargetException;

public enum ScreenType {

    LOADING(LoadingScreen.class),
    LOBBY(LobbyScreen.class),
    LOBBY_SELECTION(LobbySelectionScreen.class),
    LOG_IN(LoginScreen.class),
    MAIN_MENU(MainMenuScreen.class),
    PREFERENCES(PreferencesScreen.class),
    PLAY(PlayScreen.class),
    CONNECT(IPAddressScreen.class),
    CREDITS(CreditsScreen.class),
    SKINS(SkinsScreen.class),
    STATS(StatsScreen.class);

    private final Class<? extends Screen> screenClass;

    ScreenType(Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }

    /**
     * Returns a new instance of the specific screen
     *
     * @param myGame Game instance
     * @return screen instance
     */
    public Screen instantiate(MyGame myGame) {
        Screen newInstance = null;
        try {
            newInstance = (Screen) screenClass.getDeclaredConstructors()[0].newInstance(myGame);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInstance;
    }
}
