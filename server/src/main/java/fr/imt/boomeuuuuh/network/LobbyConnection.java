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

package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.players.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class LobbyConnection extends Thread {

    private final DatagramSocket socket;
    private final int udpPort;
    private boolean stop = false;

    public LobbyConnection() throws SocketException {
        this.socket = new DatagramSocket();
        this.udpPort = socket.getLocalPort();
        this.start();
    }

    @Override
    public void run() {
        while (!stop) {
            DatagramPacket incomingPacket = new DatagramPacket(new byte[28], 28); // TODO change buffer size to optimize
            try {
                socket.receive(incomingPacket);
                Packet packet = Packet.getFromBytes(incomingPacket.getData(), incomingPacket.getAddress());
                packet.handle();
            } catch (IOException e) {
                Boomeuuuuh.logger.severe("Error while reading incoming UDP packets from : " + e.getMessage());
            }
        }
    }

    //-------------------------GET-------------------------
    public int getPort() {
        return udpPort;
    }
    //-----------------------------------------------------

    /**
     * Sends one or more packets to a specific player through UDP
     *
     * @param player  Receiver
     * @param packets Packets to send
     */
    public void send(Player player, Packet... packets) {
        InetAddress address = player.getAddress();
        int updPort = player.getPort();

        for (Packet packet : packets) {
            byte[] packed = packet.getBytes();
            try {
                socket.send(new DatagramPacket(packed, packed.length, address, updPort));
            } catch (IOException e) {
                Boomeuuuuh.logger.severe("Impossible to send packets through UDP to " + player.getAddress());
            }
        }
    }

    /**
     * Stops the socket
     */
    public void close() {
        this.stop = true;
        this.socket.close();
    }
}
