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

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.players.Player;

public class CreateAccountPacket extends Packet {

    private final Player player;
    private final String username, password;

    /**
     * Packet received when a client requests to create an account
     * @param player player that made the request
     * @param username new account username
     * @param password new account password
     */
    public CreateAccountPacket(Player player, String username, String password) {
        super(PacketType.CREATE_ACCOUNT);

        this.player = player;
        this.username = username;
        this.password = password;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used server-side
        return null;
    }

    @Override
    public void handle() {
        if (player.isAuthentified())
            return;

        if (Boomeuuuuh.database.usernameAlreadyExists(username)) {
            DeclinePacket packet = new DeclinePacket("Username already taken...");
            player.serverConnection.send(packet);
            return;
        }

        Boomeuuuuh.database.createAccount(username, password);
        player.authenticate(username, password);
    }
}
