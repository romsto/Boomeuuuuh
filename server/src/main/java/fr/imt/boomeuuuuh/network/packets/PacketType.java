package fr.imt.boomeuuuuh.network.packets;

import com.google.common.primitives.Ints;
import fr.imt.boomeuuuuh.network.packets.both.ReadyPacket;
import fr.imt.boomeuuuuh.players.Player;
import fr.imt.boomeuuuuh.network.packets.both.DeclinePacket;
import fr.imt.boomeuuuuh.network.packets.both.TestPacket;
import fr.imt.boomeuuuuh.network.packets.client.*;

import java.nio.charset.StandardCharsets;

public enum PacketType {

    TEST {
        @Override
        public Packet make(byte[] data) {
            return new TestPacket(new String(data, StandardCharsets.UTF_8));
        }
    },
    DECLINE {
        @Override
        public Packet make(byte[] data, Player player) {
            return new DeclinePacket(new String(data, StandardCharsets.UTF_8), player);
        }
    },
    LOBBY_LIST,
    REQUEST_LOBBY_LIST {
        @Override
        public Packet make(byte[] data, Player player) {
            return new RequestLobbyListPacket(player);
        }
    },
    JOIN_LOBBY {
        @Override
        public Packet make(byte[] data, Player player) {
            return new JoinLobbyPacket(player, new String(data));
        }
    },
    LOBBY_INFO,
    SEND_CHAT {
        @Override
        public Packet make(byte[] data, Player player) {
            return new SendChatPacket(new String(data, StandardCharsets.UTF_8), player);
        }
    },
    RECEIVE_CHAT,
    CREATE_LOBBY {
        @Override
        public Packet make(byte[] data, Player player) {
            return new CreateLobbyPacket(player, new String(data));
        }
    },
    KICK,
    LEAVE {
        @Override
        public Packet make(byte[] data, Player player) {
            return new LeavePacket(player);
        }
    },
    DISCONNECT {
        @Override
        public Packet make(byte[] data, Player player) {
            return new DisconnectPacket(player);
        }
    },
    INITIALIZE_LOBBY_CONNECTION {
        @Override
        public Packet make(byte[] data, Player player) {
            return new InitializeLobbyConnectionPacket(Ints.fromByteArray(data), player);
        }
    },
    SUCCESSFULLY_JOINED,
    LOBBY_CREDENTIALS,
    ENTITY_CREATE,
    ENTITY_DESTROY,
    ENTITY_MOVE,
    PLAYER_REFERENCE,
    BOMB_PLACE,
    BOMB_PLACED,
    START_GAME,
    END_GAME,
    READY {
        @Override
        public Packet make(byte[] data, Player player) {
            return new ReadyPacket(player);
        }
    },
    CHANGE_LOBBY_NAME {
        @Override
        public Packet make(byte[] data, Player player) {
            return new ChangeNamePacket(new String(data), player);
        }
    },
    LAUNCH_GAME {
        @Override
        public Packet make(byte[] data, Player player) {
            return new LaunchGamePacket(player);
        }
    };


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

    public Packet make(byte[] data, Player player) {
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
