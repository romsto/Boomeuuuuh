package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.Server;
import fr.imt.boomeuuuuh.network.packets.Packet;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerConnection extends Thread {

    private final Player player;
    private final Socket serverSocket;
    private final BufferedReader reader;
    private final PrintStream writer;
    private boolean stop = false;

    public ServerConnection(Player player, Socket serverSocket) throws IOException {
        this.player = player;
        this.serverSocket = serverSocket;
        this.reader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.writer = new PrintStream(serverSocket.getOutputStream());
        this.start();
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    // Close connection
                    Boomeuuuuh.logger.info((player.getName() == null ? player.getAddress().toString() : player.getName()) + " left.");
                    Server.removePlayer(player);
                    break;
                }

                byte[] incomingBytes = line.getBytes();
                Packet packet = Packet.getFromBytes(incomingBytes, player);
                packet.handle();
            } catch (IOException e) {
                if (e instanceof SocketTimeoutException)
                    Boomeuuuuh.logger.info((player.getName() == null ? player.getAddress().toString() : player.getName()) + " timed out...");
                else
                    Boomeuuuuh.logger.severe("Error while reading incoming packets of " + player.getAddress() + " : " + e.getMessage());
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
    public void send(Packet... packets) {
        for (Packet packet : packets) {
            writer.println(new String(packet.getBytes()));
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
