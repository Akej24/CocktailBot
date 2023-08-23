package org.cocktailbot.drink.command.decide;

import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DecideRedisRepositoryTest {

    private static final String SUGGEST_PREFIX = "suggest:";
    private static final String TOTRY_PREFIX = "totry:";
    private static final String testToUsername = "test-to-username";
    private static final String testFromUsername = "test-from-username";
    private static final String testSuggestedDrinkName = "test-suggested-drink";

    private final Jedis testDatabase = RedisTestConfig.getInstance().getJedis();
    private DecideRedisRepository testDecideRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase.flushAll();
        testDecideRedisRepository = new DecideRedisRepository(testDatabase);
    }

    @AfterEach
    void tearDown() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully accepted suggested drink and moved it to to-try list")
    void acceptSuggestedDrink() {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, testFromUsername);
        boolean status = testDecideRedisRepository.acceptSuggestedDrink(testToUsername, testSuggestedDrinkName);
        Map<String, String> suggestedDrinksAfterAccept = testDatabase.hgetAll(SUGGEST_PREFIX + testToUsername);
        Set<String> toTryDrinksAfterAccept = testDatabase.smembers(TOTRY_PREFIX + testToUsername);
        assertTrue(status);
        assertEquals(0, suggestedDrinksAfterAccept.size());
        assertEquals(1, toTryDrinksAfterAccept.size());
    }

    @Test
    @DisplayName("Should pass when returned false, because author of suggested drink does not exist")
    void acceptSuggestedDrink_withBlankFromUser() {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, "");
        boolean status = testDecideRedisRepository.acceptSuggestedDrink(testToUsername, testSuggestedDrinkName);
        assertFalse(status);
    }

    @Test
    @DisplayName("Should pass could not move accepted drink to to-try list, because amount of to try drinks is above limit")
    void acceptSuggestedDrink_aboveToTryLimit() {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, testFromUsername);
        for(int i=0; i<52; i++) {
            testDatabase.sadd(TOTRY_PREFIX + testToUsername, testSuggestedDrinkName + i);
        }
        boolean status = testDecideRedisRepository.acceptSuggestedDrink(testToUsername, testSuggestedDrinkName);
        assertFalse(status);
    }

    @Test
    @DisplayName("Should pass when successfully rejected suggested drink and deleted it from the list")
    void rejectSuggestedDrink() {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, testFromUsername);
        boolean status = testDecideRedisRepository.rejectSuggestedDrink(testToUsername, testSuggestedDrinkName);
        Map<String, String> suggestedDrinksAfterAccept = testDatabase.hgetAll(SUGGEST_PREFIX + testToUsername);
        Set<String> toTryDrinksAfterAccept = testDatabase.smembers(TOTRY_PREFIX + testToUsername);
        assertTrue(status);
        assertEquals(0, suggestedDrinksAfterAccept.size());
        assertEquals(0, toTryDrinksAfterAccept.size());
    }

    @Test
    @DisplayName("Should pass when returned false, because author of suggested drink does not exist")
    void rejectSuggestedDrink_withBlankFromUser() {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, "");
        boolean status = testDecideRedisRepository.rejectSuggestedDrink(testToUsername, testSuggestedDrinkName);
        assertFalse(status);
    }
}