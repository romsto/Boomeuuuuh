/*
 * Copyright (c) 2022.
 * Authors : Storaï R, Faure B, Mathieu A, Garry A, Nicolau T, Bregier M.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
                PORT = 8080;
            } catch (UnknownHostException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
