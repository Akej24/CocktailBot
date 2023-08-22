package org.cocktailbot.drink.command.tried;

import dev.coly.jdat.JDATesting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriedCommandTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void onMessageReceived() throws InterruptedException {
//        JDATesting.assertGuildMessageReceivedEvent(TriedConfig.getInstance(), "!tried drink-name", "Your tried drink: drink-name has been removed");
        assertEquals("Hello null!\nWe could not remove your tried drink: drink-name",
                JDATesting.testMessageReceivedEvent(TriedConfig.getInstance(), "!tried drink-name").getContentRaw());
    }
}