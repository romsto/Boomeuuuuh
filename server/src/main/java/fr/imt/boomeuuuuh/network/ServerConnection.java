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
import fr.imt.boomeuuuuh.Server;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.players.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerConnection extends Thread {

    private final Player player;
    private final Socket serverSocket;
    private final DataInputStream reader;
    private final DataOutputStream writer;
    private boolean stop = false;

    public ServerConnection(Player player, Socket serverSocket) throws IOException {
        this.player = player;
        this.serverSocket = serverSocket;
        this.reader = new DataInputStream(serverSocket.getInputStream());
        this.writer = new DataOutputStream(serverSocket.getOutputStream());
        this.start();
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                int length = reader.readInt();
                if (length <= 0) {
                    // Close connection
                    Boomeuuuuh.logger.info((player.getName() == null ? player.getAddress().toString() : player.getName()) + " left.");
                    Server.removePlayer(player);
                    break;
                }
                byte[] incomingBytes = new byte[length];
                reader.readFully(incomingBytes);
                Packet packet = Packet.getFromBytes(incomingBytes, player);
                packet.handle();
            } catch (IOException e) {
                if (e instanceof SocketTimeoutException)
                    Boomeuuuuh.logger.info((player.getName() == null ? player.getAddress().toString() : player.getName()) + " timed out...");
                else
                    Boomeuuuuh.logger.info((player.getName() == null ? player.getAddress().toString() : player.getName()) + " left.");
                Server.removePlayer(player);
                break;
            }
        }
    }

    /**
     * Sends one or more packets through TCP
     *
     * @param packets Packets to send
     */
    public synchronized void send(Packet... packets) {
        if (stop)
            return;

        for (Packet packet : packets) {
            try {
                byte[] bytes = packet.getBytes();
                int type = bytes[0] + 126;
                if (type < 0 || type >= PacketType.values().length) {
                    System.out.println("ERROR");
                    continue;
                }
                writer.writeInt(bytes.length);
                writer.write(bytes);
            } catch (IOException e) {
                Boomeuuuuh.logger.severe("Can't send packet to " + player.getAddress() + " : " + e.getMessage());
                close();
            }
        }
    }

    /**
     * Kill the client socket
     */
    public void close() {
        this.stop = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            Boomeuuuuh.logger.warning("Cannot disconnect " + (player.getName() == null ? player.getAddress().toString() : player.getName()));
        }
    }
}
