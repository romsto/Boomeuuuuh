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

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.lobbies.LobbyState;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.ScreenType;

public class StartGamePacket extends Packet {

    public StartGamePacket() {
        super(PacketType.START_GAME);
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        final Lobby lobby = MyGame.getInstance().lobby;
        if (!MyGame.getInstance().logged || lobby == null)
            return;

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                lobby.game = new Game();
                lobby.state = LobbyState.LOADING;
                MyGame.getInstance().changeScreen(ScreenType.LOADING);
            }
        });
    }
}
