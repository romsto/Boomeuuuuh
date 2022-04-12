package fr.imt.boomeuuuuh.network.packets.server;

import com.badlogic.gdx.Gdx;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.AssetsManager;

public class EntityDestroyPacket extends Packet {

    private final int entityId;

    public EntityDestroyPacket(int entityId) {
        super(PacketType.ENTITY_DESTROY);
        this.entityId = entityId;
    }

    @Override
    protected byte[] encode() {
        // Shouldn't be used client-side
        return null;
    }

    @Override
    public void handle() {
        final Game game = Game.getInstance();

        if (!MyGame.getInstance().logged || MyGame.getInstance().lobby == null || game == null)
            return;

        if (game.player != null && entityId == game.player.getId()) {
            game.player = null;
            AssetsManager.playSound("death");
        }

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                game.removeEntity(entityId);
            }
        });
    }
}
