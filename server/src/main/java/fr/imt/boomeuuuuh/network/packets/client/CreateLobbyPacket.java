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

import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyManager;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

public class CreateLobbyPacket extends Packet {

    private final Player player;
    private final String name;

    /**
     * packet received when a player request to create a lobby
     * @param player player that made the request
     * @param name name of the new lobby
     */
    public CreateLobbyPacket(Player player, String name) {
        super(PacketType.CREATE_LOBBY);

        this.player = player;
        this.name = name;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be encoded server side
        return null;
    }

    @Override
    public void handle() {
        if (!player.isAuthentified()) {
            DeclinePacket declinePacket = new DeclinePacket("You're not authenticated.");
            player.serverConnection.send(declinePacket);
            return;
        }

        Lobby lobby = LobbyManager.startLobby(player);
        if (lobby == null) {
            DeclinePacket declinePacket = new DeclinePacket("There was an error while creating the lobby");
            player.serverConnection.send(declinePacket);
            return;
        }
        //lobby.setName(name);
        lobby.addPlayer(player);
    }
}
