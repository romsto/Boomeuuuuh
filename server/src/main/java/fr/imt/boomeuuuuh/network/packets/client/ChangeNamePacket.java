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

import fr.imt.boomeuuuuh.lobbies.LobbyManager;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

public class ChangeNamePacket extends Packet {

    private final Player player;
    private final String name;

    /**
     * Packet received when client requests to change the name of the lobby they own
     * @param name new name of the lobby
     * @param player player that made the request
     */
    public ChangeNamePacket(String name, Player player) {
        super(PacketType.CHANGE_LOBBY_NAME);

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
        if (!player.isInLobby())
            return;

        if (!player.getLobby().getOwner().equals(player)) {
            player.serverConnection.send(new DeclinePacket("You're not owner of the lobby."));
            return;
        }

        if (LobbyManager.exists(name)) {
            player.serverConnection.send(new DeclinePacket("You're not owner of the lobby."));
            return;
        }

        player.getLobby().setName(name);
    }
}
