package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.network.packets.Packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerConnection extends Thread {

    private final Socket serverSocket;
    private final BufferedReader reader;
    private final PrintStream writer;
    private boolean stop = false;

    public ServerConnection(String ipAddress) throws IOException {
        String[] address = ipAddress.split(":");
        this.serverSocket = new Socket(InetAddress.getByName(address[0]), Integer.parseInt(address[1]));
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
                    // TODO close connection
                    close();
                    break;
                }

                byte[] incomingBytes = line.getBytes();
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
            // TODO Manage this exception
        }
    }
}
