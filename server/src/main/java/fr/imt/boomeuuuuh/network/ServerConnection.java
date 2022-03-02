package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.Server;
import fr.imt.boomeuuuuh.network.packets.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerConnection extends Thread {

    private final Player player;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ServerConnection(Player player, Socket serverSocket) throws IOException {
        this.player = player;
        this.inputStream = serverSocket.getInputStream();
        this.outputStream = serverSocket.getOutputStream();

        this.start();
    }

    @Override
    public void run() {
        while (true/* TODO make possible to disconnect the player */) {
            byte[] incomingBytes = new byte[28]; // TODO change the buffer size in order to optimize
            try {
                int size = inputStream.read(incomingBytes);
                Packet packet = Packet.getFromBytes(incomingBytes);
                // TODO HANDLE THIS PACKET
            } catch (IOException e) {
                if (e instanceof SocketTimeoutException)
                    Boomeuuuuh.logger.info((player.getName() == null ? player.getAddress().toString() : player.getName()) + " timed out...");
                else
                    Boomeuuuuh.logger.severe("Error while reading incoming packets of " + player.getAddress() + " : " + e.getMessage());
                Server.removePlayer(player);
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
            try {
                outputStream.write(packet.getBytes());
            } catch (IOException e) {
                Boomeuuuuh.logger.severe("Impossible to send packets to " + player.getAddress());
            }
        }
    }
}
