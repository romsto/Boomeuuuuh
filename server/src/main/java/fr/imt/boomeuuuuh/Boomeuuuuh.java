package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.utils.ConfigFile;

import java.io.IOException;
import java.util.logging.Logger;

public class Boomeuuuuh {

    public final static Logger logger = Logger.getLogger("Boomeuuuuh");
    public static Database database;

    /**
     * Main method
     *
     * @param args passed in the launch command
     */
    public static void main(String[] args) {
        // Open database connection
        database = new Database();

        // Server startup
        int port = ConfigFile.PORT;

        try {
            Server server = new Server(port);
            server.start();
        } catch (IOException e) {
            logger.severe("Impossible to start the server on port " + port + " : " + e.getMessage());
        }
    }
}
