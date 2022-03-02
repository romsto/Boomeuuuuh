package fr.imt.boomeuuuuh.network.packets;

public abstract class Packet {

    private final PacketType packetType;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    abstract byte[] encode();

    /**
     * Gets raw data from a packet, step before sending through network
     *
     * @return bytes
     */
    public byte[] getBytes() {
        byte[] data = encode();
        byte[] packet = new byte[data.length + 1];

        packet[0] = Integer.valueOf(packetType.ordinal()).byteValue();
        System.arraycopy(data, 0, packet, 1, data.length);
        return packet;
    }

    /**
     * Transforms a byte raw data to a Packet
     *
     * @param packet raw data
     * @return Packet built
     */
    public static Packet getFromBytes(byte[] packet) {
        int type = packet[0];
        byte[] data = new byte[packet.length - 1];
        System.arraycopy(packet, 1, data, 0, packet.length - 1);

        return PacketType.values()[type].make(data);
    }
}
