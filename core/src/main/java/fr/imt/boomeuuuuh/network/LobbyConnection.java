package fr.imt.boomeuuuuh.network;

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.lobbies.Lobby;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.both.TestPacket;
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
            DatagramPacket incomingPacket = new DatagramPacket(new byte[254], 254); // TODO change buffer size to optimize
            try {
                socket.receive(incomingPacket);
                Packet packet = Packet.getFromBytes(incomingPacket.getData());
                if (packet instanceof TestPacket)
                    System.out.println("Lobby");
                packet.handle();
            } catch (IOException e) {
                System.out.println("Connection lost to lobby 1");
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
                System.out.println("Connection lost to lobby");
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
