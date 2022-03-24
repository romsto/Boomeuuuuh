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
