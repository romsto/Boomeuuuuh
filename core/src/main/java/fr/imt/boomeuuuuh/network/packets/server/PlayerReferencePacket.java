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

import com.badlogic.gdx.physics.box2d.Filter;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.Skin;

public class PlayerReferencePacket extends Packet {

    private final int entityId;
    private final String playerName;
    private final String skin;

    public PlayerReferencePacket(int entityId, String playerName, String skin) {
        super(PacketType.PLAYER_REFERENCE);
        this.entityId = entityId;
        this.playerName = playerName;
        this.skin = skin;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        Game game = Game.getInstance();

        if (!MyGame.getInstance().logged || MyGame.getInstance().lobby == null || game == null)
            return;

        Entity entity = game.getEntity(entityId);
        if (!(entity instanceof Player))
            return;

        if (playerName.equalsIgnoreCase(MyGame.getInstance().username)) {
            game.player = (Player) entity;
            ((Player) entity).isAffected = false;
            game.lastBlocX = entity.getBlocX();
            game.lastBlocY = entity.getBlocY();
            Filter filter = game.player.getBody().getFixtureList().get(0).getFilterData();
            filter.categoryBits = Entity.PLAYER_CATEGORY;
            filter.maskBits = Entity.SOLID_CATEGORY;
        }

        ((Player) entity).refer(playerName, Skin.getByDataName(skin));
    }
}
