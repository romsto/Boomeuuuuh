package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.Game.GameManager;
import fr.imt.boomeuuuuh.lobbies.Lobby;

import java.io.IOException;
import java.util.logging.Logger;

public class Boomeuuuuh {

    public final static Logger logger = Logger.getLogger("Boomeuuuuh");
    public static Database database;

    public static void main(String[] args) {
        // Open database connection
        database = new Database();

        // Server startup
        int port = 301;

        try {
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            logger.severe("Impossible to start the server on port " + port + " : " + e.getMessage());
        }

        //Test
//        try{
//        Lobby l = new Lobby(0, "Test", null);
//        l.startGame("map1");
//        }catch (Exception e){Boomeuuuuh.logger.severe(e.toString());}
    }
}
