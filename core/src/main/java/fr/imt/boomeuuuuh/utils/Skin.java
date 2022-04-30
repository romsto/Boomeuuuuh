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

    /**
     * Get the number of the skin
     *
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get the name of the skin in files
     *
     * @return data name
     */
    public String getDataName() {
        return "skin" + number;
    }

    /**
     * Get an icon to display of the current skin
     *
     * @return icon
     */
    public Image getIcon() {
        return new Image(TextureRegion.split(new Texture(Gdx.files.internal("skin/skinDown" + number + ".png")), 32, 32)[0][0]);
    }
}
