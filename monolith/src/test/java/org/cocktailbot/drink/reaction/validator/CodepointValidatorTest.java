package org.cocktailbot.drink.reaction.validator;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.cocktailbot.drink.test_environment.in_memory.InMemoryBot;
import org.cocktailbot.drink.test_environment.in_memory.InMemoryUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
class CodepointValidatorTest {

    private static final String testEmbedTitle = "test-embed-title";
    private static final String testEmptyEmbedTitle = "";

    @Autowired
    private CodepointValidator testCodepointValidator;

    @Test
    @DisplayName("Should return true when user from reaction add event exists and is not null")
    void validateReactionEvent_withExistingUser() {
        var testMessageReactionAddEvent = mock(MessageReactionAddEvent.class);
        InMemoryUser.createForMessageReactionAddEvent(testMessageReactionAddEvent);
        boolean actual = testCodepointValidator.validateReactionEvent(testMessageReactionAddEvent, testEmbedTitle);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Should return false when user from reaction add event is bot")
    void validateReactionEvent_withBot() {
        var testMessageReactionAddEvent = mock(MessageReactionAddEvent.class);
        InMemoryBot.createForMessageReactionAddEvent(testMessageReactionAddEvent);
        boolean actual = testCodepointValidator.validateReactionEvent(testMessageReactionAddEvent, testEmbedTitle);
        assertFalse(actual);
    }

    @Test
    @DisplayName("Should return false when user exists and embed title is empty")
    void validateReactionEvent_withExistingUser_and_emptyEmbedTitle() {
        var testMessageReactionAddEvent = mock(MessageReactionAddEvent.class);
        InMemoryUser.createForMessageReactionAddEvent(testMessageReactionAddEvent);
        boolean actual = testCodepointValidator.validateReactionEvent(testMessageReactionAddEvent, testEmptyEmbedTitle);
        assertFalse(actual);
    }
}
