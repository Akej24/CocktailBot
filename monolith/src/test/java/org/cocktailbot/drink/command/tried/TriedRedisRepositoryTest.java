package org.cocktailbot.drink.command.tried;

import org.cocktailbot.drink.test_environment.base.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TriedRedisRepositoryTest extends IntegrationTest {

    private static final String PREFIX = "totry:";
    private static final String testUsername = "test-username";
    private static final String testDrinkName = "test-drink-name";

    private Jedis testDatabase;
    private TriedRedisRepository testTriedRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        testTriedRedisRepository = new TriedRedisRepository(testDatabase);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should return true, when successfully removed existing drink")
    void removeExistingTriedDrink() {
        testDatabase.sadd(PREFIX + testUsername, testDrinkName);
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