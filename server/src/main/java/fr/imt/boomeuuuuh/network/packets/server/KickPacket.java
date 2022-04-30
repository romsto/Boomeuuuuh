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

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.nio.charset.StandardCharsets;

public class KickPacket extends Packet {

    private final String reason;

    /**
     * Packet to inform the client it has been kicked from a lobby
     * @param reason reason it has been kicked
     */
    public KickPacket(String reason) {
        super(PacketType.KICK);

        this.reason = reason;
    }

    @Override
    protected byte[] encode() {
        return reason.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        // Shouldn't be handled server-side
    }
}
