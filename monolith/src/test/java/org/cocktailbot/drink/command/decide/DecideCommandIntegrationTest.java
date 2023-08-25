package org.cocktailbot.drink.command.decide;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.test_environment.base.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DecideCommandIntegrationTest extends IntegrationTest {

    private static final String SUGGEST_PREFIX = "suggest:";
    private static final String testToUsername = "test user";
    private static final String testFromUsername = "test-from-username";
    private static final String testSuggestedDrinkName = "test-suggested-drink";

    @Autowired
    private PrefixValidator prefixValidator;
    private DecideCommand testDecideCommand;
    private Jedis testDatabase;

    @BeforeEach
    void setUp() {
        testDatabase = new Jedis(getRedisContainerHostName(), getRedisContainerPort());
        testDatabase.flushAll();
        var testDecideRedisRepository = new DecideRedisRepository(testDatabase);
        var testDecideService = new DecideService(testDecideRedisRepository);
        testDecideCommand = new DecideCommand(prefixValidator, testDecideService);
    }

    @AfterEach
    void cleanUp() {
        testDatabase.flushAll();
    }

    @Test
    @DisplayName("Should pass when accepted existing drink")
    void createMessageReceivedEvent_withExistingSuggestedDrink_and_accept() throws InterruptedException {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, testFromUsername);
        String actual = JDATesting.testMessageReceivedEvent(testDecideCommand, "!accept " + testSuggestedDrinkName).getContentRaw();
        assertTrue(actual.contains("has been accepted"));
    }

    @Test
    @DisplayName("Should pass when could not accept not existing drink")
    void createMessageReceivedEvent_withNotExistingSuggestedDrink_and_accept() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testDecideCommand, "!accept " + testSuggestedDrinkName).getContentRaw();
        assertTrue(actual.contains("We could not accept"));
    }

    @Test
    @DisplayName("Should pass when rejected existing drink")
    void createMessageReceivedEvent_withExistingSuggestedDrink_and_reject() throws InterruptedException {
        testDatabase.hset(SUGGEST_PREFIX + testToUsername, testSuggestedDrinkName, testFromUsername);
        String actual = JDATesting.testMessageReceivedEvent(testDecideCommand, "!reject " + testSuggestedDrinkName).getContentRaw();
        assertTrue(actual.contains("has been rejected"));
    }

    @Test
    @DisplayName("Should pass when could not reject not existing drink")
    void createMessageReceivedEvent_withNotExistingSuggestedDrink_and_reject() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testDecideCommand, "!reject " + testSuggestedDrinkName).getContentRaw();
        assertTrue(actual.contains("We could not reject"));
    }
}