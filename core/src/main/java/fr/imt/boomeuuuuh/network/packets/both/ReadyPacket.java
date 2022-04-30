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

package fr.imt.boomeuuuuh.network.packets.both;

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.ScreenType;

public class ReadyPacket extends Packet {

    public ReadyPacket() {
        super(PacketType.READY);
    }

    @Override
    protected byte[] encode() {
        return new byte[0];
    }

    @Override
    public void handle() {
        Game game = Game.getInstance();
        Lobby lobby = MyGame.getInstance().lobby;

        if (!MyGame.getInstance().logged || lobby == null || game == null)
            return;

        if (lobby.state != LobbyState.LOADING)
            return;

        lobby.state = LobbyState.PLAYING;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MyGame.getInstance().changeScreen(ScreenType.PLAY);
            }
        });
    }
}
