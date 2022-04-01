package fr.imt.boomeuuuuh.network.packets.server;

import com.badlogic.gdx.physics.box2d.World;
import fr.imt.boomeuuuuh.Game;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.HardBlock;
import fr.imt.boomeuuuuh.entities.Player;
import fr.imt.boomeuuuuh.entities.SoftBlock;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.utils.Location;

public class EntityCreatePacket extends Packet {

    private final int entityId, entityType;
    private final Location location;

    public EntityCreatePacket(int entityId, int entityType, Location location) {
        super(PacketType.ENTITY_CREATE);
        this.entityId = entityId;
        this.entityType = entityType;
        this.location = location;
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

        World world = game.getWorld();

        Entity e = null;

        switch (entityType) {
            case 60:
                e = new Player(entityId, location, world);
                break;
            case 50:
                e = new SoftBlock(entityId, location, world);
                break;
            case 40:
                e = new HardBlock(entityId, location, world);
                break;
        }

        if (e == null)
            return;

        game.spawnEntity(e);
    }
}
