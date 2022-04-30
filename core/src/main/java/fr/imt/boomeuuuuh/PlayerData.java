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

package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.network.packets.client.SelectSkinPacket;
import fr.imt.boomeuuuuh.network.packets.client.UnlockSkinPacket;
import fr.imt.boomeuuuuh.utils.Skin;

public class PlayerData {

    public int gold;
    public int level;
    public int kills;
    public int maxkillstreak;
    public int wins;
    public String currentSkin;
    public String[] unlockedSkins;

    public Skin getCurrentSkin() {
        return Skin.getByDataName(currentSkin);
    }

    /**
     * Check if the player has a specific skin
     *
     * @param skin to check
     * @return boolean
     */
    public boolean hasSkin(Skin skin) {
        for (String unlockedSkin : unlockedSkins) {
            if (unlockedSkin.equalsIgnoreCase(skin.getDataName()))
                return true;
        }
        return false;
    }

    /**
     * Select the current player skin (send to the server)
     *
     * @param skin to select
     */
    public void selectSkin(Skin skin) {
        this.currentSkin = skin.getDataName();

        MyGame.getInstance().serverConnection.send(new SelectSkinPacket(currentSkin));
    }

    /**
     * Tries to unlock a new skin (check the gold)
     *
     * @param skin to unlock
     */
    public void unlockSkin(Skin skin) {
        MyGame.getInstance().serverConnection.send(new UnlockSkinPacket(skin.getDataName()));
    }
}
