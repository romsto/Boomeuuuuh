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

package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class PlayerInfoPacket extends Packet {

    private final int maxBomb, power, speed, kills;

    public PlayerInfoPacket(int maxBomb, int power, int speed, int kills) {
        super(PacketType.PLAYER_INFO);

        this.maxBomb = maxBomb;
        this.power = power;
        this.speed = speed;
        this.kills = kills;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        Game game = Game.getInstance();
        if (game == null)
            return;
        if (game.player_bomb != maxBomb || game.player_speed != speed || game.player_bomb_power != power) {
            AssetsManager.playSound("powerup");
        }
        game.player_bomb = maxBomb;
        game.player_speed = speed;
        game.player_kills = kills;
        game.player_bomb_power = power;
    }
}
