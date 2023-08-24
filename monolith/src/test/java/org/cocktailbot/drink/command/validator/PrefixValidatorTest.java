package org.cocktailbot.drink.command.validator;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.cocktailbot.drink.in_memory.InMemoryBot;
import org.cocktailbot.drink.in_memory.InMemoryUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PrefixValidatorTest {

    private static final String testCorrectCommandName = "!test-correct-command-name";

    @Autowired
    private PrefixValidator testPrefixValidatorTest;

    @Test
    @DisplayName("Should return true when command name is valid and author is not bot")
    void validateCommand_withExistingUser() {
        var testMessage = mock(Message.class);
        when(testMessage.getContentRaw())
                .thenReturn("!test-correct-command-name + some-content");
        var testMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(testMessageReceivedEvent.getMessage())
                .thenReturn(testMessage);
        InMemoryUser.createForMessageReceivedEvent(testMessageReceivedEvent);
        boolean actual = testPrefixValidatorTest.validateCommand(testMessageReceivedEvent, testCorrectCommandName);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Should return false when command name is invalid")
    void validateCommand_withBot() {
        var testMessage = mock(Message.class);
        when(testMessage.getContentRaw())
                .thenReturn("!test-incorrect-command-name + some-content");
        var testMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(testMessageReceivedEvent.getMessage())
                .thenReturn(testMessage);
        InMemoryUser.createForMessageReceivedEvent(testMessageReceivedEvent);
        boolean actual = testPrefixValidatorTest.validateCommand(testMessageReceivedEvent, testCorrectCommandName);
        assertFalse(actual);
    }

    @Test
    @DisplayName("Should return false when author is bot")
    void validateCommand_withExistingUser_and_emptyEmbedTitle() {
        var testMessage = mock(Message.class);
        when(testMessage.getContentRaw())
                .thenReturn("!test-correct-command-name + some-content");
        var testMessageReceivedEvent = mock(MessageReceivedEvent.class);
        when(testMessageReceivedEvent.getMessage())
                .thenReturn(testMessage);
        InMemoryBot.createForMessageReceivedEvent(testMessageReceivedEvent);
        boolean actual = testPrefixValidatorTest.validateCommand(testMessageReceivedEvent, testCorrectCommandName);
        assertFalse(actual);
    }

}