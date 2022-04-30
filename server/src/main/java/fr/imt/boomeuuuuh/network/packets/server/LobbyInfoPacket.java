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

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

import java.nio.charset.StandardCharsets;

public class LobbyInfoPacket extends Packet {

    private final String rawData;

    /**
     * Packet containing all necessary info on the current lobby (name, owner, if open, players)
     * @param lobby lobby the packet is about
     */
    public LobbyInfoPacket(Lobby lobby) {
        super(PacketType.LOBBY_INFO);
        StringBuilder builder = new StringBuilder(lobby.getName()).append("|").append(lobby.getOwner().getName()).append("|").append(lobby.isOpen() ? 1 : 0).append("|");
        String delimiter = "";
        for (Player player : lobby.getPlayers()) {
            builder.append(delimiter).append(player.getName());
            delimiter = "/";
        }
        this.rawData = builder.toString();
    }

    @Override
    protected byte[] encode() {
        return rawData.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Shouldn't be handled server side
    }
}
