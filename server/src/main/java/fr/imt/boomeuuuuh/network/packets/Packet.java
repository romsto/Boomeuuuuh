package fr.imt.boomeuuuuh.network.packets;

import fr.imt.boomeuuuuh.Player;
import fr.imt.boomeuuuuh.Server;

import java.net.InetAddress;

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
        byte[] packet = new byte[data.length + 1];

        packet[0] = (byte) (packetType.ordinal() - 127);
        System.arraycopy(data, 0, packet, 1, data.length);
        return packet;
    }

    /**
     * Transforms a byte raw data to a Packet
     *
     * @param packet raw data
     * @return Packet built
     */
    public static Packet getFromBytes(byte[] packet, InetAddress address) {
        int type = packet[0] + 127;
        byte[] data = extractData(packet);
        PacketType packetType = PacketType.values()[type];

        Packet instance = packetType.make(data);
        if (instance == null)
            instance = packetType.make(data, Server.getPlayer(address));
        return instance;
    }

    /**
     * Transforms a byte raw data to a Packet
     *
     * @param packet raw data
     * @return Packet built
     */
    public static Packet getFromBytes(byte[] packet, Player player) {
        int type = packet[0] + 127;
        byte[] data = extractData(packet);
        PacketType packetType = PacketType.values()[type];

        Packet instance = packetType.make(data);
        if (instance == null)
            instance = packetType.make(data, player);
        return instance;
    }

    /**
     * Extracts data from a packet (removing the header)
     *
     * @param packet Packet to extract
     * @return extracted data
     */
    private static byte[] extractData(byte[] packet) {
        byte[] data = new byte[packet.length - 1];
        System.arraycopy(packet, 1, data, 0, packet.length - 1);
        return data;
    }
}
