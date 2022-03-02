package fr.imt.test;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.TestPacket;
import org.junit.jupiter.api.Assertions;

public class Test {

    @org.junit.jupiter.api.Test
    public void testTestPacket() {
        TestPacket destruct = new TestPacket("Ceci est test");
        byte[] bytes = destruct.getBytes();
        TestPacket reconstruct = (TestPacket) Packet.getFromBytes(bytes);
        Assertions.assertEquals(destruct.getMessage(), reconstruct.getMessage());
    }
}
