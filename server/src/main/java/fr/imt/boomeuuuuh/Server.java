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

package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.players.Player;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread {

    private static ServerSocket serverSocket;
    private static final Map<InetAddress, Player> players = new HashMap<>();

    private boolean stop = false;

    /**
     * Create an instance of server. (Usually only one instance runs at once)
     *
     * @param port to host the TCP server
     * @throws IOException in case of socket issue
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                Socket socket = serverSocket.accept();
                InetAddress address = socket.getInetAddress();
                Boomeuuuuh.logger.info("Accepting new connection " + address.toString());

                Player player = new Player(socket);
                players.put(address, player);
            } catch (IOException e) {
                Boomeuuuuh.logger.severe("Error while accepting connections : " + e.getMessage());
            }
        }
    }

    /**
     * Removes a player from the online players. It also disconnects him from all the instances
     *
     * @param player Player to remove
     */
    public static void removePlayer(Player player) {
        player.disconnect();
        players.remove(player.getAddress());
    }

    /**
     * Returns a player according to its address
     *
     * @param address query
     * @return found player
     */
    public static Player getPlayer(InetAddress address) {
        return players.get(address);
    }

    /**
     * Returns a player according to its address
     *
     * @param address query
     * @return found player
     */
    public static Player getPlayer(InetAddress address, int port) {
        return players.values().stream().filter(player -> player.getAddress().equals(address) && player.getPort() == port).findFirst().get();
    }

    /**
     * Checks if a payer with an address already exists
     *
     * @param address to check
     * @return true or false
     */
    public static boolean exists(InetAddress address) {
        return players.containsKey(address);
    }

    /**
     * Returns the online players list
     *
     * @return Online players
     */
    public static Collection<Player> getPlayers() {
        return players.values();
    }

    /**
     * Stops the server
     */
    public void close() {
        this.stop = true;
    }
}
