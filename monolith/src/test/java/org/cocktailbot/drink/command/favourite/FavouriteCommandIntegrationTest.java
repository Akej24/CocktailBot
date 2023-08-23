package org.cocktailbot.drink.command.favourite;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FavouriteCommandIntegrationTest {

    private static final String PREFIX = "favourite:";
    private static final String testUsername = "test user";
    private static final String testDrinkName1 = "test-drink-name1";
    private static final String testDrinkName2 = "test-drink-name2";

    private final Jedis testDatabase = RedisTestConfig.getInstance().getJedis();
    private FavouriteCommand testIngredientCommand;

    @BeforeEach
    void setUp() {
        testDatabase.flushAll();
        FavouriteRedisRepository testFavouriteRedisRepository = new FavouriteRedisRepository(testDatabase);
        FavouriteService testFavouriteService = new FavouriteService(testFavouriteRedisRepository);
        testIngredientCommand = new FavouriteCommand(PrefixValidator.getInstance(), testFavouriteService);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass returned success message for existing favourite drinks")
    void createMessageReceivedEvent_withExistingFavouriteDrinks() throws InterruptedException {
        testDatabase.sadd(PREFIX + testUsername, testDrinkName1, testDrinkName2);
        String actual = JDATesting.testMessageReceivedEvent(testIngredientCommand, "!favourites").getContentRaw();
        assertTrue(actual.contains("These are your favourite drinks"));
    }

    @Test
    @DisplayName("Should pass when returned failure message for not existing favourite drinks")
    void createMessageReceivedEvent_withNotExistingFavouriteDrinks() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testIngredientCommand, "!favourites").getContentRaw();
        assertTrue(actual.contains("You do not have any favourite drinks"));
    }
}