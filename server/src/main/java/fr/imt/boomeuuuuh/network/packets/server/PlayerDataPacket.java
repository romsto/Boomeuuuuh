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
import fr.imt.boomeuuuuh.players.PlayerData;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class PlayerDataPacket extends Packet {

    private final Player player;

    /**
     * Packet containing stats of a player (gold, level, kills, max kill streak, wins, name, current skin, owned skins)
     * @param player player the packet is about
     */
    public PlayerDataPacket(Player player) {
        super(PacketType.PLAYER_DATA);
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        PlayerData data = player.getPlayerData();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(data.getGold()));
        baos.writeBytes(Ints.toByteArray(data.getLevel()));
        baos.writeBytes(Ints.toByteArray(data.getKills()));
        baos.writeBytes(Ints.toByteArray(data.getMaxkillstreak()));
        baos.writeBytes(Ints.toByteArray(data.getWins()));
        StringBuilder builder = new StringBuilder(player.getName()).append("|").append(data.getCurrentSkin()).append("|");
        String delimiter = "";
        for (String unlockedSkin : data.getUnlockedSkins()) {
            builder.append(delimiter).append(unlockedSkin);
            delimiter = "/";
        }
        baos.writeBytes(builder.toString().getBytes(StandardCharsets.UTF_8));
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
