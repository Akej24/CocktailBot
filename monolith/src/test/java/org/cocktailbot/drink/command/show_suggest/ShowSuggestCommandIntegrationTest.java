package org.cocktailbot.drink.command.show_suggest;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ShowSuggestCommandIntegrationTest {

    private static final String PREFIX = "suggest:";
    private static final String testUsername = "test user";
    private static final String testFromUsername = "test-from-username";
    private static final String testDrinkName1 = "test-drink-name1";
    private static final String testDrinkName2 = "test-drink-name2";

    @Autowired
    private PrefixValidator prefixValidator;
    @Autowired
    private RedisTestConfig redisTestConfig;
    private Jedis testDatabase;
    private ShowSuggestCommand testShowSuggestCommand;

    @BeforeEach
    void setUp() {
        testDatabase = redisTestConfig.getJedis();
        testDatabase.flushAll();
        ShowSuggestService testShowSuggestService = new ShowSuggestService(new ShowSuggestRedisRepository(testDatabase));
        testShowSuggestCommand = new ShowSuggestCommand(prefixValidator, testShowSuggestService);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    void onMessageReceived_withExistingDrinks() throws InterruptedException {
        testDatabase.hset(PREFIX + testUsername, Map.of(testDrinkName1, testFromUsername, testDrinkName2, testFromUsername));
        String expected = "Hello null!\nThere are your suggested drinks:\n- " + testDrinkName2 + " ~ " + testFromUsername + "\n- " + testDrinkName1 + " ~ " + testFromUsername + "\n";
        String actual = JDATesting.testMessageReceivedEvent(testShowSuggestCommand, "!showsuggested").getContentRaw();
        assertEquals(expected, actual);
    }

    @Test
    void onMessageReceived_withNotExistingDrinks() throws InterruptedException {
        String expected = "Hello null!\nYour do not have any suggested drinks";
        String actual = JDATesting.testMessageReceivedEvent(testShowSuggestCommand, "!showsuggested").getContentRaw();
        assertEquals(expected, actual);
    }
}