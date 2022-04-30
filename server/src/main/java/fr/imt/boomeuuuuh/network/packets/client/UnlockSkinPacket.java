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

package fr.imt.boomeuuuuh.network.packets.client;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.server.PlayerDataPacket;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.players.PlayerData;

public class UnlockSkinPacket extends Packet {

    private final Player player;
    private final String name;

    public UnlockSkinPacket(String name, Player player) {
        super(PacketType.UNLOCK_SKIN);

        this.name = name;
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        PlayerData playerData = player.getPlayerData();

        if (playerData.hasSkin(name)) {
            player.serverConnection.send(new DeclinePacket("You already have this skin!"));
            return;
        }

        if (playerData.getGold() < 100) {
            player.serverConnection.send(new DeclinePacket("You do not have enough gold!"));
            return;
        }

        playerData.addGold(-100);
        playerData.unlockSkin(name);

        player.serverConnection.send(new PlayerDataPacket(player));
    }
}
