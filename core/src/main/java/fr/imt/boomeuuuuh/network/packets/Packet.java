package fr.imt.boomeuuuuh.network.packets;

import fr.imt.boomeuuuuh.network.packets.both.TestPacket;

import java.util.ArrayList;

public abstract class Packet {

    private final PacketType packetType;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    protected abstract byte[] encode();

    public abstract void handle();

    /**
     * Gets raw data from a packet, step before sending through network
     *
     * @return bytes
     */
    public byte[] getBytes() {
        byte[] data = encode();
        byte[] packet = new byte[data.length + 2];
        packet[0] = (byte) (packetType.ordinal() - 126);
        packet[1] = (byte) (data.length - 126);
        System.arraycopy(data, 0, packet, 2, data.length);
        return packet;
    }

    /**
     * Transforms a byte raw data to a Packet
     *
     * @param packet raw data
     * @return Packet built
     */
    public static Packet getFromBytes(byte[] packet) {
        int type = packet[0] + 126;

        if (type < 0 || type >= PacketType.values().length) {
            System.out.println("Unknown packet : " + type);
            System.out.println("Length : " + packet.length);
            ArrayList<Byte> bytes = new ArrayList<>();
            for (byte b : packet) {
                bytes.add(b);
            }
            System.out.println("Data : " + bytes);
            return new TestPacket("Unknown Packet");
        }

        int size = packet[1] + 126;

        try {
            byte[] data = extractData(packet, size);
            PacketType packetType = PacketType.values()[type];

            return packetType.make(data);
        } catch (Exception e) {
            System.out.println("Error at packet " + type + " (" + PacketType.values()[type] + ")");
            System.out.println(e.getMessage());
        }

        return new TestPacket("There was an error...");
    }

    /**
     * Extracts data from a packet (removing the header)
     *
     * @param packet Packet to extract
     * @param size   of the packet
     * @return extracted data
     */
    private static byte[] extractData(byte[] packet, int size) {
        byte[] data = new byte[size];
        System.arraycopy(packet, 2, data, 0, size);
        return data;
    }
}
