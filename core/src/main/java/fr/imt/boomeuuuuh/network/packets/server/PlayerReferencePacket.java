package fr.imt.boomeuuuuh.network.packets.server;

import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.Player;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;

public class PlayerReferencePacket extends Packet {

    private final int entityId;
    private final String playerName;
    private final String skin;

    public PlayerReferencePacket(int entityId, String playerName, String skin) {
        super(PacketType.PLAYER_REFERENCE);
        this.entityId = entityId;
        this.playerName = playerName;
        this.skin = skin;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        Game game = Game.getInstance();

        if (!MyGame.getInstance().logged || MyGame.getInstance().lobby == null || game == null)
            return;

        Entity entity = game.getEntity(entityId);
        if (!(entity instanceof Player))
            return;

        if (playerName.equalsIgnoreCase(MyGame.getInstance().username)) {
            game.player = (Player) entity;
            ((Player) entity).isAffected = false;
            game.lastBlocX = entity.getBlocX();
            game.lastBlocY = entity.getBlocY();
        }

        ((Player) entity).refer(playerName, skin);
    }
}
