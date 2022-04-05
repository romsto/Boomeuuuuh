package fr.imt.boomeuuuuh.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;

public class ConfigFile {

    public static InetAddress ADDRESS;
    public static int PORT;

    static {
        File file = new File("config.boomeuuuuh");
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("localhost:25566");
                fileWriter.close();

            }

            String str = new String(Files.readAllBytes(file.toPath()));
            String[] split = str.split(":");
            ADDRESS = InetAddress.getByName(split[0]);
            PORT = Integer.parseInt(split[1]);
        } catch (IOException e) {
            try {
                ADDRESS = InetAddress.getByName("localhost");
                PORT = 25566;
            } catch (UnknownHostException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
