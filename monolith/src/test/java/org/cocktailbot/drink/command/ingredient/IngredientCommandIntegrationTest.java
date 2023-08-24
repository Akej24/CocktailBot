package org.cocktailbot.drink.command.ingredient;

import dev.coly.jdat.JDATesting;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IngredientCommandIntegrationTest {

    private static final String testExistingIngredient = "Bitter lemon";
    private static final String testNotExistingIngredient = "not-existing-ingredient";

    @Autowired
    private PrefixValidator prefixValidator;
    @Autowired
    private DrinkJsonClient drinkJsonClient;
    @Autowired
    private DrinkJsonResponseReader drinkJsonResponseReader;
    private IngredientCommand testIngredientCommand;

    @BeforeEach
    void setUp() {
        IngredientService testIngredientService = new IngredientService(drinkJsonClient, drinkJsonResponseReader);
        testIngredientCommand = new IngredientCommand(prefixValidator, testIngredientService);
    }

    @Test
    @DisplayName("Should pass returned success message for existing ingredient")
    void createMessageReceivedEvent_withExistingIngredient() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testIngredientCommand, "!ingredient " + testExistingIngredient).getContentRaw();
        assertTrue(actual.contains("Ingredient random fact:"));
    }

    @Test
    @DisplayName("Should pass when returned failure message for not existing ingredient")
    void createMessageReceivedEvent_withNotExistingIngredient() throws InterruptedException {
        String actual = JDATesting.testMessageReceivedEvent(testIngredientCommand, "!ingredient " + testNotExistingIngredient).getContentRaw();
        assertTrue(actual.contains("Your ingredient does not exist"));
    }
}