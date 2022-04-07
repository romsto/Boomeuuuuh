package fr.imt.test;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.Server;
import fr.imt.boomeuuuuh.network.LobbyConnection;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.both.TestPacket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class NetworkTest {

    @Test
    public void testTestPacket() throws UnknownHostException {
        TestPacket destruct = new TestPacket("nothing");
        byte[] bytes = destruct.getBytes();
        TestPacket reconstruct = (TestPacket) Packet.getFromBytes(bytes, InetAddress.getLocalHost());
        Assertions.assertEquals(destruct.getMessage(), reconstruct.getMessage());
    }

    @Test
    public void testTCPSocket() throws IOException, InterruptedException {
        Server server = new Server(25500);
        server.start();
        Socket socket = new Socket(InetAddress.getLocalHost(), 25500);
        Thread.sleep(100);
        //Assertions.assertFalse(Server.getPlayers().isEmpty());
        PrintStream writer = new PrintStream(socket.getOutputStream());
        writer.println(new String(new TestPacket("Test").getBytes()));
        socket.close();
        Thread.sleep(100);
        Assertions.assertTrue(Server.getPlayers().isEmpty());
        server.close();
    }

    @Test
    public void testUDPSocket() throws IOException {
        LobbyConnection lobbyConnection = new LobbyConnection();
        int port = lobbyConnection.getPort();
        DatagramSocket socket = new DatagramSocket();
        byte[] testPacket = new TestPacket("Test").getBytes();
        byte[] testPacket1 = new TestPacket("Test 1").getBytes();
        DatagramPacket packet = new DatagramPacket(testPacket, testPacket.length, InetAddress.getLocalHost(), port);
        DatagramPacket packet1 = new DatagramPacket(testPacket1, testPacket1.length, InetAddress.getLocalHost(), port);
        socket.send(packet);
        socket.send(packet1);
    }

    @Test
    public void testBytesConversions() {
        int integer = new Random().nextInt(5000);
        byte[] integerToByte = Ints.toByteArray(integer);
        Assertions.assertEquals(integer, Ints.fromByteArray(integerToByte));

        String message = "This is a spécial message with UTF-8 chars";
        byte[] messageToBytes = message.getBytes(StandardCharsets.UTF_8);
        Assertions.assertEquals(message, new String(messageToBytes, StandardCharsets.UTF_8));


        String test = "test" + "Ǝ" + "tes4";
        Assertions.assertEquals(test, new String(test.getBytes()));
    }

    @Test
    public void testDataInputOutputStreams() throws IOException {
        ServerSocket serverSocket = new ServerSocket(46);
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 46);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeInt(25);
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Socket socket = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        Assertions.assertEquals(25, dataInputStream.readInt());
        socket.close();
    }
}
