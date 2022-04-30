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

public class EntityCreatePacket extends Packet {

    private final int entityId, entityType;
    private final Location location;

    /**
     * Packet to tell the client of the creation of an entity in game of types currently handled by the client (Including : hardBlock, softBlock)
     * @param entityId ID assigned
     * @param entityType Type of the entity, see enum
     * @param location Location the entity was placed at
     */
    public EntityCreatePacket(int entityId, int entityType, Location location) {
        super(PacketType.ENTITY_CREATE);
        this.entityId = entityId;
        this.entityType = entityType;
        this.location = location;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.write(entityType);
        baos.writeBytes(location.toByteArray());
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
