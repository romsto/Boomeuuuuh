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

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Location;

import java.io.ByteArrayOutputStream;

public class EntityMovePacket extends Packet {

    private final int entityId;
    private final Location location;
    private final int speed;

    /**
     * Packet to tell the client to move an entity
     * @param entityId ID of the entity
     * @param location new location of the entity
     * @param speed speed at which the entity moves
     */
    public EntityMovePacket(int entityId, Location location, int speed) {
        super(PacketType.ENTITY_MOVE);
        this.entityId = entityId;
        this.location = location;
        this.speed = speed;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.writeBytes(location.toByteArray());
        baos.writeBytes(Ints.toByteArray(speed));
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
