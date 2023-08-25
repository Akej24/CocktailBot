package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.test_environment.base.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToTryRedisRepositoryTest extends IntegrationTest {

    private static final String PREFIX = "totry:";
    private static final String testUsername = "test-username";
    private static final DrinkName testDrinkName1 = new DrinkName("test-drink-name1");
    private static final DrinkName testDrinkName2 = new DrinkName("test-drink-name2");

    private Jedis testDatabase;
    private ToTryRedisRepository testToTryRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        testToTryRedisRepository = new ToTryRedisRepository(testDatabase);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully got user existing to try drinks")
    void getUserExistingToTryDrinks() {
        testDatabase.sadd(PREFIX + testUsername, testDrinkName1.name(), testDrinkName2.name());
        Set<DrinkName> drinks = testToTryRedisRepository.getUserToTryDrinks(testUsername);
        assertEquals(2, drinks.size());
        assertTrue(drinks.contains(testDrinkName1));
        assertTrue(drinks.contains(testDrinkName2));
    }

    @Test
    @DisplayName("Should pass when returned empty set for not existing to try drinks")
    void getUserNotExistingToTryDrinks() {
        Set<DrinkName> drinks = testToTryRedisRepository.getUserToTryDrinks(testUsername);
        assertTrue(drinks.isEmpty());
    }
}