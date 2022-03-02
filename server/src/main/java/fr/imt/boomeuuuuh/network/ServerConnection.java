package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerConnection extends Thread {

    private final Player player;
    private final Socket serverSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ServerConnection(Player player, Socket serverSocket) throws IOException {
        this.player = player;
        this.serverSocket = serverSocket;
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
                // TODO Manage this error and the timeout
            }
        }
    }

    public void send(Packet... packets) {
        for (Packet packet : packets) {
            try {
                outputStream.write(packet.getBytes());
            } catch (IOException e) {
                // TODO Manage this error
            }
        }
    }
}
