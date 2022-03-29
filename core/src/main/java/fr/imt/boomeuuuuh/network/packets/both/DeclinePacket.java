package fr.imt.boomeuuuuh.network.packets.both;

import com.badlogic.gdx.Screen;
import fr.imt.boomeuuuuh.MyGame;
import fr.imt.boomeuuuuh.network.packets.Packet;
import fr.imt.boomeuuuuh.network.packets.PacketType;
import fr.imt.boomeuuuuh.screens.LoginScreen;

import java.nio.charset.StandardCharsets;

public class DeclinePacket extends Packet {

    private final String reason;

    public DeclinePacket(String reason) {
        super(PacketType.DECLINE);
        this.reason = reason;
    }

    @Override
    protected byte[] encode() {
        return reason.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void handle() {
        Screen currentScreen = MyGame.getInstance().getCurrentScreen();

        if (currentScreen instanceof LoginScreen) {
            LoginScreen loginScreen = (LoginScreen) currentScreen;

            loginScreen.label.setText(reason);
        }
    }
}
