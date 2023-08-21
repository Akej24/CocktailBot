package org.cocktailbot.drink.command.validator;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.cocktailbot.drink.in_memory.InMemoryBot;
import org.cocktailbot.drink.in_memory.InMemoryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrefixValidatorTest {

    private static final String testCorrectCommandName = "!test-correct-command-name";
    private PrefixValidator testPrefixValidatorTest;

    @BeforeEach
    void setUp() {
        testPrefixValidatorTest = PrefixValidator.getInstance();
    }

    @Test
    @DisplayName("Should return true when command name is valid and author is not bot")
    void validateCommand_withExistingUser() {
        var testMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(testMessageReceivedEvent.getMessage())
                .thenReturn(new MessageBuilder()
                        .setContent("!test-correct-command-name + some-content")
                        .build()
                );
        InMemoryUser.createForMessageReceivedEvent(testMessageReceivedEvent);
        boolean actual = testPrefixValidatorTest.validateCommand(testMessageReceivedEvent, testCorrectCommandName);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Should return false when command name is invalid")
    void validateCommand_withBot() {
        var testMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(testMessageReceivedEvent.getMessage())
                .thenReturn(new MessageBuilder()
                        .setContent("!test-incorrect-command-name + some-content")
                        .build()
                );
        InMemoryUser.createForMessageReceivedEvent(testMessageReceivedEvent);
        boolean actual = testPrefixValidatorTest.validateCommand(testMessageReceivedEvent, testCorrectCommandName);
        assertFalse(actual);
    }

    @Test
    @DisplayName("Should return false when author is bot")
    void validateCommand_withExistingUser_and_emptyEmbedTitle() {
        var testMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(testMessageReceivedEvent.getMessage())
                .thenReturn(new MessageBuilder()
                        .setContent("!test-correct-command-name + some-content")
                        .build()
                );
        InMemoryBot.createForMessageReceivedEvent(testMessageReceivedEvent);
        boolean actual = testPrefixValidatorTest.validateCommand(testMessageReceivedEvent, testCorrectCommandName);
        assertFalse(actual);
    }

}