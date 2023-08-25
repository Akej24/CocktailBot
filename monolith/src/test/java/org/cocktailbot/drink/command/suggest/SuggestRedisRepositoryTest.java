package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.test_environment.base.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SuggestRedisRepositoryTest extends IntegrationTest {

    private static final String PREFIX = "suggest:";
    private static final String fromTestUsername = "from-test-username";
    private static final String toTestUsername = "to-test-username";
    private static final String testDrinkName = "test-drink-name";

    private Jedis testDatabase;
    private SuggestRedisRepository testSuggestRedisRepository;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        testSuggestRedisRepository = new SuggestRedisRepository(testDatabase);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when successfully added suggestion to database")
    void saveDrinkToSuggestedUsername() {
        boolean actual = testSuggestRedisRepository.saveDrinkToSuggestedUsername(fromTestUsername, testDrinkName,toTestUsername);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Should pass when could not add more suggestions than limit")
    void saveDrinkToSuggestedUsername_aboveLimit() {
        boolean status = true;
        for(int i=0; i<52; i++) {
            status = testSuggestRedisRepository.saveDrinkToSuggestedUsername(fromTestUsername + i, testDrinkName + i, toTestUsername);
        }
        assertFalse(status);
        long actualSize = testDatabase.hlen(PREFIX + toTestUsername);
        assertEquals(50, actualSize);
    }
}