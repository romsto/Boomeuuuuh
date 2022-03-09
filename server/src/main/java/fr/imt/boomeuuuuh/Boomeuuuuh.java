package fr.imt.boomeuuuuh;

import java.io.IOException;
import java.util.logging.Logger;

public class Boomeuuuuh {

    public final static Logger logger = Logger.getLogger("Boomeuuuuh");

    public static void main(String[] args) {
        // Server startup
        int port = 301;

        try {
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            logger.severe("Impossible to start the server on port " + port + " : " + e.getMessage());
        }
    }
}
