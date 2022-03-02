package fr.imt.boomeuuuuh;

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

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
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
        players.remove(player.getAddress());
        // TODO disconnect player
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
}
