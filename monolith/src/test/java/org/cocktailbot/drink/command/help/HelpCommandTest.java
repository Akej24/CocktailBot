package org.cocktailbot.drink.command.help;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelpCommandTest {

    @Autowired
    private PrefixValidator prefixValidator;
    private HelpCommand testHelpCommand;

    @BeforeEach
    void setUp() {
        testHelpCommand = new HelpCommand(prefixValidator);
    }

    @Test
    @DisplayName("Should pass when help command returns right message")
    void onMessageReceived() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testHelpCommand, "!help").getContentRaw();
        assertTrue(actual.contains("list of available commands"));
    }
}