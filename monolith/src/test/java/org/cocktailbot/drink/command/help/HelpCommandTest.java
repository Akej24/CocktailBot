package org.cocktailbot.drink.command.help;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpCommandTest {

    private HelpCommand testHelpCommand;

    @BeforeEach
    void setUp() {
        testHelpCommand = new HelpCommand(PrefixValidator.getInstance());
    }

    @Test
    @DisplayName("Should pass when help command returns right message")
    void onMessageReceived() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testHelpCommand, "!help").getContentRaw();
        assertTrue(actual.contains("list of available commands"));
    }
}