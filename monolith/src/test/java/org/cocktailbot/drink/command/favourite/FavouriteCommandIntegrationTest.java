package org.cocktailbot.drink.command.favourite;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FavouriteCommandIntegrationTest {

    private static final String PREFIX = "favourite:";
    private static final String testUsername = "test user";
    private static final String testDrinkName1 = "test-drink-name1";
    private static final String testDrinkName2 = "test-drink-name2";

    @Autowired
    private PrefixValidator prefixValidator;
    @Autowired
    private RedisTestConfig redisTestConfig;

    private FavouriteCommand testFavouriteCommand;
    private Jedis testDatabase;

    @BeforeEach
    void setUp() {
        testDatabase = redisTestConfig.getJedis();
        testDatabase.flushAll();
        var testFavouriteRedisRepository = new FavouriteRedisRepository(testDatabase);
        var testFavouriteService = new FavouriteService(testFavouriteRedisRepository);
        testFavouriteCommand = new FavouriteCommand(prefixValidator, testFavouriteService);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass returned success message for existing favourite drinks")
    void createMessageReceivedEvent_withExistingFavouriteDrinks() throws InterruptedException {
        testDatabase.sadd(PREFIX + testUsername, testDrinkName1, testDrinkName2);
        String actual = JDATesting.testMessageReceivedEvent(testFavouriteCommand, "!favourites").getContentRaw();
        assertTrue(actual.contains("These are your favourite drinks"));
    }

    @Test
    @DisplayName("Should pass when returned failure message for not existing favourite drinks")
    void createMessageReceivedEvent_withNotExistingFavouriteDrinks() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testFavouriteCommand, "!favourites").getContentRaw();
        assertTrue(actual.contains("You do not have any favourite drinks"));
    }
}