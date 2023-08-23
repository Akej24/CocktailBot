package org.cocktailbot.drink.command.to_try;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToTryCommandIntegrationTest {

    private static final String TO_TRY_PREFIX = "totry:";
    private static final String testUsername = "test user";
    private static final DrinkName testDrinkName1 = new DrinkName("test-drink-name1");
    private static final DrinkName testDrinkName2 = new DrinkName("test-drink-name2");

    private final Jedis testDatabase = RedisTestConfig.getInstance().getJedis();
    private ToTryCommand testToTryCommand;

    @BeforeEach
    void setUp() {
        testDatabase.flushAll();
        ToTryService testToTryService = new ToTryService(new ToTryRedisRepository(testDatabase));
        testToTryCommand = new ToTryCommand(PrefixValidator.getInstance(), testToTryService);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully returned two user to try drinks")
    void createMessageReceivedEvent_and_getUserToTryDrinks() throws InterruptedException {
        testDatabase.sadd(TO_TRY_PREFIX + testUsername, testDrinkName1.name(), testDrinkName2.name());
        String expected = "Hello null!\nThere are your drinks to try:\n- " + testDrinkName2.name() + "\n- " + testDrinkName1.name() + "\n";
        String actual = JDATesting.testMessageReceivedEvent(testToTryCommand, "!totry").getContentRaw();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should pass when returned zero to try drinks, because they are not existing")
    void createMessageReceivedEvent_and_cannotGetUserToTryDrinks() throws InterruptedException {
        String expected = "Hello null!\nYou do not have any to try drinks";
        String actual = JDATesting.testMessageReceivedEvent(testToTryCommand, "!totry").getContentRaw();
        assertEquals(expected, actual);
    }
}