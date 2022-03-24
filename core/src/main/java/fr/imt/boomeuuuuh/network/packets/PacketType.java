package fr.imt.boomeuuuuh.network.packets;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.server.*;
import fr.imt.boomeuuuuh.utils.Location;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public enum PacketType {

    TEST,
    DECLINE,
    LOBBY_LIST,
    REQUEST_LOBBY_LIST,
    JOIN_LOBBY,
    LOBBY_INFO {
        @Override
        public Packet make(byte[] data) {
            return new LobbyInfoPacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    SEND_CHAT,
    RECEIVE_CHAT,
    CREATE_LOBBY,
    KICK,
    LEAVE,
    DISCONNECT,
    INITIALIZE_LOBBY_CONNECTION,
    SUCCESSFULLY_JOINED,
    LOBBY_CREDENTIALS,
    ENTITY_CREATE {
        @Override
        public Packet make(byte[] data) {
            byte[] id = Arrays.copyOfRange(data, 0, 4);
            int type = data[4];
            byte[] location = Arrays.copyOfRange(data, 5, 7);
            return new EntityCreatePacket(Ints.fromByteArray(id), type, Location.fromBytesArray(location));
        }
    },
    ENTITY_DESTROY {
        @Override
        public Packet make(byte[] data) {
            return new EntityDestroyPacket(Ints.fromByteArray(data));
        }
    },
    ENTITY_MOVE,
    PLAYER_REFERENCE,
    BOMB_PLACE,
    BOMB_PLACED {
        @Override
        public Packet make(byte[] data) {
            byte[] id = Arrays.copyOfRange(data, 0, 4);
            int power = data[4];
            byte[] location = Arrays.copyOfRange(data, 5, 7);
            return new BombPlacedPacket(Ints.fromByteArray(id), power, Location.fromBytesArray(location));
        }
    },
    START_GAME,
    END_GAME {
        @Override
        public Packet make(byte[] data) {
            return new EndGamePacket();
        }
    },
    READY,
    CHANGE_LOBBY_NAME,
    LAUNCH_GAME,
    PLAYER_INFO,
    LOGIN,
    CREATE_ACCOUNT,
    PLAYER_DATA;

    public Packet make(byte[] data) {

        /*
        This method won't be used : Server can't receive server packets
         */

        return new Packet(TEST) {
            @Override
            protected byte[] encode() {
                return null;
            }

            @Override
            public void handle() {
                // Nothing
            }
        };
    }
}
