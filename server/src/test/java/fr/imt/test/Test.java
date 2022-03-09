package fr.imt.test;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.TestPacket;
import org.junit.jupiter.api.Assertions;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

    @org.junit.jupiter.api.Test
    public void testTestPacket() throws UnknownHostException {
        TestPacket destruct = new TestPacket("Ceci est test");
        byte[] bytes = destruct.getBytes();
        TestPacket reconstruct = (TestPacket) Packet.getFromBytes(bytes, InetAddress.getLocalHost());
        Assertions.assertEquals(destruct.getMessage(), reconstruct.getMessage());
    }
}
