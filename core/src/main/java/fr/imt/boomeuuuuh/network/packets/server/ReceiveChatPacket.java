package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class ReceiveChatPacket extends Packet {

    private final String message;

    public ReceiveChatPacket(String message) {
        super(PacketType.RECEIVE_CHAT);

        this.message = message;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        MyGame.getInstance().lobby.chat += "\n" + message;
    }
}
