package fr.imt.boomeuuuuh.network.packets;

public abstract class Packet {

    private PacketType packetType;

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    abstract byte[] encode();

    public byte[] getBytes() {
        byte[] data = encode();
        byte[] packet = new byte[data.length + 1];

        packet[0] = (byte) packetType.ordinal();

        return packet;
    }

    public static Packet getFromBytes(byte[] packet) {
        int type = packet[0];
        byte[] data = new byte[packet.length - 1];
        System.arraycopy(packet, 1, data, 0, packet.length);

        return PacketType.values()[type].make(data);
    }
}
