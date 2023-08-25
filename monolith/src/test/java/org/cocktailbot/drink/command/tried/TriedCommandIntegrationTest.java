package org.cocktailbot.drink.command.tried;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.test_environment.base.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TriedCommandIntegrationTest extends IntegrationTest {

    private static final String TO_TRY_PREFIX = "totry:";
    private static final String testUsername = "test user";
    private static final String testDrinkName = "test-drink-name";

    @Autowired
    private PrefixValidator prefixValidator;
    private Jedis testDatabase;
    private TriedCommand testTriedCommand;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        TriedService testTriedService = new TriedService(new TriedRedisRepository(testDatabase));
        testTriedCommand = new TriedCommand(prefixValidator, testTriedService);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when validated command and deleted user tried drink from database and returned correct message")
    void createMessageReceivedEvent_and_removeTriedDrink() throws InterruptedException {
        testDatabase.sadd(TO_TRY_PREFIX + testUsername, testDrinkName);
        String expected = "Hello null!\nYour tried drink: " + testDrinkName + " has been removed";
        String actual = JDATesting.testMessageReceivedEvent(testTriedCommand, "!tried " + testDrinkName).getContentRaw();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should pass when validated command, but user tried drink does not exist, so could not remove and returned correct message")
    void createMessageReceivedEvent_and_cannotRemoveTriedDrink() throws InterruptedException {
        String expected = "Hello null!\nWe could not remove your tried drink: " + testDrinkName;
        String actual = JDATesting.testMessageReceivedEvent(testTriedCommand, "!tried " + testDrinkName).getContentRaw();
        assertEquals(expected, actual);
    }
}