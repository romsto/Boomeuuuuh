package fr.imt.boomeuuuuh.network.packets;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.both.ReadyPacket;
import fr.imt.boomeuuuuh.network.packets.server.*;
import fr.imt.boomeuuuuh.utils.Location;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public enum PacketType {

    TEST,
    DECLINE {
        @Override
        public Packet make(byte[] data) {
            return new DeclinePacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    LOBBY_LIST {
        @Override
        public Packet make(byte[] data) {
            return new LobbyListPacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    REQUEST_LOBBY_LIST,
    JOIN_LOBBY,
    LOBBY_INFO {
        @Override
        public Packet make(byte[] data) {
            return new LobbyInfoPacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    SEND_CHAT,
    RECEIVE_CHAT {
        @Override
        public Packet make(byte[] data) {
            return new ReceiveChatPacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    CREATE_LOBBY,
    KICK {
        @Override
        public Packet make(byte[] data) {
            return new KickPacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    LEAVE,
    DISCONNECT,
    INITIALIZE_LOBBY_CONNECTION,
    SUCCESSFULLY_JOINED {
        @Override
        public Packet make(byte[] data) {
            return new SuccessfullyJoinedPacket();
        }
    },
    LOBBY_CREDENTIALS {
        @Override
        public Packet make(byte[] data) {
            return new LobbyCredentialsPacket(Ints.fromByteArray(data));
        }
    },
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
    ENTITY_MOVE {
        @Override
        public Packet make(byte[] data) {
            byte[] id = Arrays.copyOfRange(data, 0, 4);
            byte[] location = Arrays.copyOfRange(data, 4, 6);
            return new EntityMovePacket(Ints.fromByteArray(id), Location.fromBytesArray(location));
        }
    },
    PLAYER_REFERENCE {
        @Override
        public Packet make(byte[] data) {
            byte[] id = Arrays.copyOfRange(data, 0, 4);
            byte[] string = Arrays.copyOfRange(data, 4, data.length);
            String[] split = new String(string, StandardCharsets.UTF_8).split("[|]");
            return new PlayerReferencePacket(Ints.fromByteArray(id), split[0], split[1]);
        }
    },
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
    START_GAME {
        @Override
        public Packet make(byte[] data) {
            return new StartGamePacket();
        }
    },
    END_GAME {
        @Override
        public Packet make(byte[] data) {
            return new EndGamePacket();
        }
    },
    READY {
        @Override
        public Packet make(byte[] data) {
            return new ReadyPacket();
        }
    },
    CHANGE_LOBBY_NAME,
    LAUNCH_GAME,
    PLAYER_INFO {
        @Override
        public Packet make(byte[] data) {
            byte[] maxBomb = Arrays.copyOfRange(data, 0, 4);
            byte[] power = Arrays.copyOfRange(data, 4, 8);
            byte[] speed = Arrays.copyOfRange(data, 8, 12);
            byte[] kills = Arrays.copyOfRange(data, 12, 16);
            return new PlayerInfoPacket(Ints.fromByteArray(maxBomb), Ints.fromByteArray(power), Ints.fromByteArray(speed), Ints.fromByteArray(kills));
        }
    },
    LOGIN,
    CREATE_ACCOUNT,
    PLAYER_DATA {
        @Override
        public Packet make(byte[] data) {
            return new PlayerDataPacket(data);
        }
    },
    CHANGE_BLOC;

    public Packet make(byte[] data) {

        /*
        This method won't be used
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
