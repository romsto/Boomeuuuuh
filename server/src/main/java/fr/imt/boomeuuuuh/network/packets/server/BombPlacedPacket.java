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

public class BombPlacedPacket extends Packet {

    private final int entityId, power;
    private final Location location;

    /**
     * Packet to tell the target client that a bomb has been placed in game
     * @param entityId ID assigned to the bomb
     * @param power power of the bomb
     * @param location location the bomb was placed
     */
    public BombPlacedPacket(int entityId, int power, Location location) {
        super(PacketType.BOMB_PLACED);
        this.entityId = entityId;
        this.power = power;
        this.location = location;
    }

    @Override
    protected byte[] encode() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(Ints.toByteArray(entityId));
        baos.write(power);
        baos.writeBytes(location.toByteArray());
        return baos.toByteArray();
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
