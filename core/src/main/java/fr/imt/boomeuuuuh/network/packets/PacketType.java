package fr.imt.boomeuuuuh.network.packets;

public enum PacketType {

    TEST,
    DECLINE,
    LOBBY_LIST,
    REQUEST_LOBBY_LIST,
    JOIN_LOBBY,
    LOBBY_INFO,
    SEND_CHAT,
    RECEIVE_CHAT,
    CREATE_LOBBY,
    KICK,
    LEAVE,
    DISCONNECT,
    INITIALIZE_LOBBY_CONNECTION,
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
    READY,
    CHANGE_LOBBY_NAME,
    LAUNCH_GAME;

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
