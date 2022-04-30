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

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

import java.io.ByteArrayOutputStream;

public class PlayerInfoPacket extends Packet {

    private final Player player;

    /**
     * Packet containing info on a player in game (max bombs, bomb power, speed, kills)
     * @param player player the packet is about
     */
    public PlayerInfoPacket(Player player) {
        super(PacketType.PLAYER_INFO);

        this.player = player;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baous = new ByteArrayOutputStream();
        baous.writeBytes(Ints.toByteArray(player.getMaxBombs()));
        baous.writeBytes(Ints.toByteArray(player.getBombPower()));
        baous.writeBytes(Ints.toByteArray(player.getSpeed()));
        baous.writeBytes(Ints.toByteArray(player.getGameKills()));
        return baous.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
