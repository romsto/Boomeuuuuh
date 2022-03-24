package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.network.packets.Packet;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerConnection extends Thread {

    private final Socket serverSocket;
    private final DataInputStream reader;
    private final DataOutputStream writer;
    private boolean stop = false;

    public ServerConnection(String ipAddress) throws IOException {
        String[] address = ipAddress.split(":");
        this.serverSocket = new Socket(InetAddress.getByName(address[0]), Integer.parseInt(address[1]));
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
                    // TODO close connection
                    close();
                    break;
                }
                byte[] incomingBytes = new byte[length];
                reader.readFully(incomingBytes);
                Packet packet = Packet.getFromBytes(incomingBytes);
                packet.handle();
            } catch (IOException e) {
                if (e instanceof SocketTimeoutException)
                    System.out.println("to be replaced"); // TODO Timeout
                else
                    System.out.println("to be replaced"); // TODO Manage error
                close();
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
            try {
                byte[] bytes = packet.getBytes();
                writer.writeInt(bytes.length);
                writer.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO Manage exception
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
            // TODO Manage this exception
        }
    }
}
