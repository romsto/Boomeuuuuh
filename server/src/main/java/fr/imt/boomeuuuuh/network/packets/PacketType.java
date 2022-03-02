package fr.imt.boomeuuuuh.network.packets;

public enum PacketType {

    TEST() {
        @Override
        Packet make(byte[] data) {
            return new TestPacket(new String(data));
        }
    };

    abstract Packet make(byte[] data);
}
