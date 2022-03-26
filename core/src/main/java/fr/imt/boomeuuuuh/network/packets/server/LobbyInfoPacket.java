package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

import java.util.Arrays;
import java.util.List;

public class LobbyInfoPacket extends Packet {

    private final String name;
    private final int owner;
    private final boolean open;
    private final List<String> players;

    public LobbyInfoPacket(String rawData) {
        super(PacketType.LOBBY_INFO);
        String[] data = rawData.split("ǥ");
        this.name = data[0];
        this.owner = Integer.parseInt(data[1]);
        this.open = data[2].equals("1");
        String[] rawPlayers = data[3].split("ǭ");
        this.players = Arrays.asList(rawPlayers);
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        // TODO
    }
}
