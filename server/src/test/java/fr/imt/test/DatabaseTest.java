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

package fr.imt.test;

import fr.imt.boomeuuuuh.Database;
import fr.imt.boomeuuuuh.players.PlayerData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    private static final Database database = new Database();

    @Test
    public void testCreateAccount() {
        if (!database.usernameAlreadyExists("test"))
            database.createAccount("test", "test");
        Assertions.assertTrue(database.usernameAlreadyExists("test"));
    }

    @Test
    public void testGold() {
        database.setGold("test", 5);
        PlayerData playerData = database.getPlayerData("test");
        Assertions.assertEquals(5, playerData.getGold());
    }

    @Test
    public void testConnection() {
        Assertions.assertTrue(database.login("test", "test"));
    }
}
