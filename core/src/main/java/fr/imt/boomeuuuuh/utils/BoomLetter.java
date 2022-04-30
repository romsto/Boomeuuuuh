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

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
