package org.cocktailbot.drink.reaction.favourite;

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
    private static final String testDrinkNameFromEmbed = "test-drink-name-from-embed";

    private Jedis testDatabase;
    private FavouriteRepository testFavouriteRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        testFavouriteRedisRepository = new FavouriteRedisRepository(testDatabase);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully added drink with favourite: prefix to test redis database")
    void saveDrinkToFavourites() {
        testFavouriteRedisRepository.addUserFavouriteDrink(testUsername, testDrinkNameFromEmbed);
        Set<String> members = testDatabase.smembers(PREFIX + testUsername);
        assertTrue(members.contains(testDrinkNameFromEmbed));
    }

    @Test
    @DisplayName("Should pass when could not add more drinks than limit")
    void saveDrinkToFavourites_aboveLimit() {
        for(int i=0; i<52; i++) {
            testFavouriteRedisRepository.addUserFavouriteDrink(testUsername, testDrinkNameFromEmbed + i);
        }
        Set<String> members = testDatabase.smembers(PREFIX + testUsername);
        assertEquals(50, members.size());
    }

    @Test
    @DisplayName("Should pass when successfully removed drink with favourite: prefix from test redis database")
    void removedDrinkFromFavourites() {
        testDatabase.sadd(PREFIX + testUsername, testDrinkNameFromEmbed);
        Set<String> membersAfterAdd = testDatabase.smembers(PREFIX + testUsername);
        assertTrue(membersAfterAdd.contains(testDrinkNameFromEmbed));

        testFavouriteRedisRepository.removeUserFavouriteDrink(testUsername, testDrinkNameFromEmbed);
        Set<String> membersAfterRemove = testDatabase.smembers(PREFIX + testUsername);
        assertFalse(membersAfterRemove.contains(testDrinkNameFromEmbed));
    }

}
