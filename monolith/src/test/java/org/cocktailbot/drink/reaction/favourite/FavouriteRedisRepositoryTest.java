package org.cocktailbot.drink.reaction.favourite;

import org.cocktailbot.drink.config.RedisTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FavouriteRedisRepositoryTest {

    private static final String PREFIX = "favourite:";
    private static final String testUsername = "test-username";
    private static final String testDrinkNameFromEmbed = "test-drink-name-from-embed";

    private final Jedis testRedisDatabase = RedisTestConfig.getInstance().getJedis();
    private FavouriteRepository testFavouriteRedisRepository;

    @BeforeEach
    void setUp() {
        testRedisDatabase.flushAll();
        testFavouriteRedisRepository = new FavouriteRedisRepository(testRedisDatabase);
    }

    @AfterEach
    void cleanUp() {
        testRedisDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully added drink with favourite: prefix to test redis database")
    void saveDrinkToFavourites() {
        testFavouriteRedisRepository.addUserFavouriteDrink(testUsername, testDrinkNameFromEmbed);
        Set<String> members = testRedisDatabase.smembers(PREFIX + testUsername);
        assertTrue(members.contains(testDrinkNameFromEmbed));
    }

    @Test
    @DisplayName("Should pass when successfully removed drink with favourite: prefix from test redis database")
    void removedDrinkFromFavourites() {
        testRedisDatabase.sadd(PREFIX + testUsername, testDrinkNameFromEmbed);
        Set<String> membersAfterAdd = testRedisDatabase.smembers(PREFIX + testUsername);
        assertTrue(membersAfterAdd.contains(testDrinkNameFromEmbed));

        testFavouriteRedisRepository.removeUserFavouriteDrink(testUsername, testDrinkNameFromEmbed);
        Set<String> membersAfterRemove = testRedisDatabase.smembers(PREFIX + testUsername);
        assertFalse(membersAfterRemove.contains(testDrinkNameFromEmbed));
    }

}
