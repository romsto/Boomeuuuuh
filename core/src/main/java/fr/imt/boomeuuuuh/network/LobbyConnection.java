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

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.client.InitializeLobbyConnectionPacket;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class LobbyConnection extends Thread {

    private final DatagramSocket socket;
    private final int udpPort;
    private final InetAddress address;
    private boolean stop = false;

    public LobbyConnection(InetAddress address, int port) throws SocketException {
        this.socket = new DatagramSocket();
        this.udpPort = port;
        this.address = address;
        this.start();
        MyGame.getInstance().serverConnection.send(new InitializeLobbyConnectionPacket(socket.getLocalPort()));
    }

    @Override
    public void run() {
        while (!stop) {
            DatagramPacket incomingPacket = new DatagramPacket(new byte[256], 256);
            try {
                socket.receive(incomingPacket);
                Packet packet = Packet.getFromBytes(incomingPacket.getData());
                packet.handle();
            } catch (IOException e) {
                System.out.println("Connection lost to lobby 1 " + e.getMessage());
                MyGame myGame = MyGame.getInstance();
                close();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MyGame.getInstance().changeScreen(ScreenType.LOBBY_SELECTION);
                    }
                });

                Lobby lobby = myGame.lobby;
                if (lobby == null)
                    return;
                if (lobby.game != null)
                    lobby.game.dispose();
                lobby.game = null;

                myGame.lobby = null;
            }
        }
    }

    //-------------------------GET-------------------------

    /**
     * Get the port of the current connection
     *
     * @return udp port
     */
    public int getPort() {
        return udpPort;
    }

    /**
     * Get the address of the current connection
     *
     * @return inetaddress
     */
    public InetAddress getAddress() {
        return address;
    }
    //-----------------------------------------------------

    /**
     * Sends one or more packets to a specific player through UDP
     *
     * @param packets Packets to send
     */
    public void send(Packet... packets) {

        for (Packet packet : packets) {
            byte[] packed = packet.getBytes();
            try {
                socket.send(new DatagramPacket(packed, packed.length, address, udpPort));
            } catch (IOException e) {
                System.out.println("Connection lost to lobby " + e.getMessage());
                MyGame myGame = MyGame.getInstance();
                close();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MyGame.getInstance().changeScreen(ScreenType.LOBBY_SELECTION);
                    }
                });

                Lobby lobby = myGame.lobby;
                if (lobby == null)
                    return;
                if (lobby.game != null)
                    lobby.game.dispose();
                lobby.game = null;

                myGame.lobby = null;
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
