package fr.imt.boomeuuuuh.network;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.client.InitializeLobbyConnectionPacket;

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
            DatagramPacket incomingPacket = new DatagramPacket(new byte[28], 28); // TODO change buffer size to optimize
            try {
                socket.receive(incomingPacket);
                System.out.println("New PAcket");
                Packet packet = Packet.getFromBytes(incomingPacket.getData());
                packet.handle();
            } catch (IOException e) {
                // TODO manage this error
            }
        }
    }

    //-------------------------GET-------------------------
    public int getPort() {
        return udpPort;
    }

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
                // TODO Manage this error
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
