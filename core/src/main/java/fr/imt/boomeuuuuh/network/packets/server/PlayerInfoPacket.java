package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class PlayerInfoPacket extends Packet {

    private final int maxBomb, power, speed, kills;

    public PlayerInfoPacket(int maxBomb, int power, int speed, int kills) {
        super(PacketType.PLAYER_INFO);

        this.maxBomb = maxBomb;
        this.power = power;
        this.speed = speed;
        this.kills = kills;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        Game game = Game.getInstance();
        if (game == null)
            return;
        if (game.player_bomb != maxBomb || game.player_speed != speed || game.player_bomb_power != power) {
            AssetsManager.playSound("powerup");
        }
        game.player_bomb = maxBomb;
        game.player_speed = speed;
        game.player_kills = kills;
        game.player_bomb_power = power;
    }
}
