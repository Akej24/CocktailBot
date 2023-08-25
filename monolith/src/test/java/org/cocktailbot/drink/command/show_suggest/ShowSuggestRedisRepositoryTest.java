package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;
import org.cocktailbot.drink.test_environment.base.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShowSuggestRedisRepositoryTest extends IntegrationTest {

    private static final String PREFIX = "suggest:";
    private static final String testToUsername = "test-to-username";
    private static final String testFromUsername = "test-from-username";
    private static final String testDrinkName1 = "test-drink-name1";
    private static final String testDrinkName2 = "test-drink-name2";

    private Jedis testDatabase;
    private ShowSuggestRedisRepository testShowSuggestRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        testShowSuggestRedisRepository = new ShowSuggestRedisRepository(testDatabase);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully got two existing elements for user")
    void getExistingSuggestedDrinksForUsername() {
        testDatabase.hset(PREFIX + testToUsername, Map.of(testDrinkName1, testFromUsername, testDrinkName2, testFromUsername));
        Map<DrinkName, Username> suggestedDrinks = testShowSuggestRedisRepository.getSuggestedDrinksForUsername(testToUsername);
        assertEquals(2, suggestedDrinks.size());
        assertTrue(suggestedDrinks.containsValue(new Username(testFromUsername)));
        assertTrue(suggestedDrinks.containsKey(new DrinkName(testDrinkName1)));
        assertTrue(suggestedDrinks.containsKey(new DrinkName(testDrinkName2)));
    }

    @Test
    @DisplayName("Should pass when successfully got zero existing elements for user")
    void getNotExistingSuggestedDrinksForUsername() {
        Map<DrinkName, Username> suggestedDrinks = testShowSuggestRedisRepository.getSuggestedDrinksForUsername(testToUsername);
        assertEquals(0, suggestedDrinks.size());
    }
}