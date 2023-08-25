package org.cocktailbot.drink.command.favourite;

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
class FavouriteRedisRepositoryTest extends IntegrationTest {

    private static final String PREFIX = "favourite:";
    private static final String testUsername = "test-username";
    private static final String testFavouriteDrinkName1 = "test-favourite-drink1";
    private static final String testFavouriteDrinkName2 = "test-favourite-drink2";

    private Jedis testDatabase;
    private FavouriteRedisRepository testFavouriteRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        testFavouriteRedisRepository = new FavouriteRedisRepository(testDatabase);
    }

    @AfterEach
    void tearDown() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when returned two existing favourite drinks")
    void getUserExistingFavouriteDrinks() {
        testDatabase.sadd(PREFIX + testUsername, testFavouriteDrinkName1, testFavouriteDrinkName2);
        Set<Favourite> favourites = testFavouriteRedisRepository.getUserFavouriteDrinks(testUsername);
        assertEquals(2, favourites.size());
        assertTrue(favourites.contains(new Favourite(testFavouriteDrinkName1)));
        assertTrue(favourites.contains(new Favourite(testFavouriteDrinkName2)));
    }

    @Test
    @DisplayName("Should pass when returned zero not existing favourite drinks")
    void getUserNotExistingFavouriteDrinks() {
        Set<Favourite> favourites = testFavouriteRedisRepository.getUserFavouriteDrinks(testUsername);
        assertEquals(0, favourites.size());
    }
}