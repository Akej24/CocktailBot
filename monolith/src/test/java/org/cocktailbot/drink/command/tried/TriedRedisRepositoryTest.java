package org.cocktailbot.drink.command.tried;

import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

class TriedRedisRepositoryTest {

    private static final String PREFIX = "totry:";
    private static final String testUsername = "test-username";
    private static final String testDrinkName = "test-drink-name";

    private final Jedis testRedisDatabase = RedisTestConfig.getInstance().getJedis();
    private TriedRedisRepository testTriedRedisRepository;

    @BeforeEach
    void setUp() {
        testRedisDatabase.flushAll();
        testTriedRedisRepository = new TriedRedisRepository(testRedisDatabase);
    }

    @AfterEach
    void cleanUp() {
        testRedisDatabase.flushAll();
    }

    @Test
    @DisplayName("Should return true, when successfully removed existing drink")
    void removeExistingTriedDrink() {
        testRedisDatabase.sadd(PREFIX + testUsername, testDrinkName);
        boolean actual = testTriedRedisRepository.removeTriedDrink(testUsername, testDrinkName);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Should return false, when tried to remove not existing drink")
    void removeNotExistingTriedDrink() {
        boolean actual = testTriedRedisRepository.removeTriedDrink(PREFIX + testUsername, testDrinkName);
        assertFalse(actual);
    }
}