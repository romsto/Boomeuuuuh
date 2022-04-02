package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.screens.ScreenType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ServerConnection extends Thread {

    private final Socket serverSocket;
    private final DataInputStream reader;
    private final DataOutputStream writer;
    private boolean stop = false;

    public ServerConnection(InetAddress ipAddress, int port) throws IOException {
        this.serverSocket = new Socket(ipAddress, port);
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
                    System.out.println("Connection lost to server");
                    MyGame.getInstance().connected = false;
                    close();
                    MyGame.getInstance().serverConnection = null;
                    MyGame.getInstance().changeScreen(ScreenType.PLAY);
                    break;
                }
                byte[] incomingBytes = new byte[length];
                reader.readFully(incomingBytes);
                Packet packet = Packet.getFromBytes(incomingBytes);
                packet.handle();
            } catch (IOException e) {
                System.out.println("Connection lost to server");
                MyGame.getInstance().connected = false;
                close();
                MyGame.getInstance().serverConnection = null;
                MyGame.getInstance().changeScreen(ScreenType.PLAY);
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
                System.out.println("Connection lost to server");
                MyGame.getInstance().connected = false;
                close();
                MyGame.getInstance().serverConnection = null;
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
        }
    }
}
