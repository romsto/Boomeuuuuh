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

import fr.imt.boomeuuuuh.lobbies.LobbyJoiningState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.server.SuccessfullyJoinedPacket;
import fr.imt.boomeuuuuh.players.Player;

public class InitializeLobbyConnectionPacket extends Packet {

    private final int port;
    private final Player player;

    public InitializeLobbyConnectionPacket(int port, Player player) {
        super(PacketType.INITIALIZE_LOBBY_CONNECTION);

        this.port = port;
        this.player = player;
    }

    @Override
    protected byte[] encode() {
        // Never used on server-side
        return null;
    }

    @Override
    public void handle() {
        if (!player.isAuthentified()) {
            DeclinePacket declinePacket = new DeclinePacket("You're not authenticated.");
            player.serverConnection.send(declinePacket);
            return;
        }

        player.setPort(port);
        player.setJoinedLobbyState(LobbyJoiningState.CONNECTED);
        player.serverConnection.send(new SuccessfullyJoinedPacket());
    }
}
