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

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.util.Arrays;
import java.util.List;

public class LobbyInfoPacket extends Packet {

    private final String name;
    private final String owner;
    private final boolean open;
    private final List<String> players;

    public LobbyInfoPacket(String rawData) {
        super(PacketType.LOBBY_INFO);
        String[] data = rawData.split("[|]");
        this.name = data[0];
        this.owner = data[1];
        this.open = data[2].equals("1");
        String[] rawPlayers = data[3].split("/");
        this.players = Arrays.asList(rawPlayers);
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        if (MyGame.getInstance().lobby == null)
            MyGame.getInstance().lobby = new Lobby();
        Lobby lobby = MyGame.getInstance().lobby;
        lobby.name = name;
        lobby.owner = owner;
        lobby.isOwner = MyGame.getInstance().username.equalsIgnoreCase(owner);
        lobby.players = players;
        lobby.open = open;
    }
}
