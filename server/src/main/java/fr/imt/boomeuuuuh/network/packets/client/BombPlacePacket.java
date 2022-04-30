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

import fr.imt.boomeuuuuh.lobbies.LobbyState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Location;
import fr.imt.boomeuuuuh.players.Player;

public class BombPlacePacket extends Packet {

    private final Player player;
    private final Location location;

    /**
     * Packet received when client requests to plant a bomb
     * @param player player who sent the request
     * @param location location where the bomb should be placed in the player's game
     */
    public BombPlacePacket(Player player, Location location) {
        super(PacketType.BOMB_PLACE);
        this.player = player;
        this.location = location;
    }


    @Override
    protected byte[] encode() {
        // Shouldn't be used server side
        return null;
    }

    @Override
    public void handle() {
        if (!player.isInLobby() || player.getLobby().getState() != LobbyState.PLAYING) { //TODO : I changed the == to !=, check if it makes sense
            // decline
            player.serverConnection.send(new DeclinePacket("You are not currently playing"));
            return;
        }

        //Send request to game manager
        player.getLobby().getGameManager().placeBomb(player, location);
    }
}
