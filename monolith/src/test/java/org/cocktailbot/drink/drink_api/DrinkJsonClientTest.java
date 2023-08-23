package org.cocktailbot.drink.drink_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DrinkJsonClientTest {

    private static final String testDrinkName = "test-drink-name";

    private DrinkJsonClient testDrinkJsonClient;

    @BeforeEach
    void setUp() {
        testDrinkJsonClient = DrinkJsonClient.getInstance();
    }

    @Test
    @DisplayName("Should pass when returned valid json for random drink request")
    void getRandomDrink() {
        String response = testDrinkJsonClient.getRandomDrink();
        assertTrue(response.startsWith("{") && response.endsWith("}"));
        assertTrue(response.contains("drinks"));
    }

    @Test
    @DisplayName("Should pass when returned valid json for any drink request")
    void getDrink() {
        String response = testDrinkJsonClient.getDrink(testDrinkName);
        assertTrue(response.startsWith("{") && response.endsWith("}"));
        assertTrue(response.contains("drinks"));
    }

    @Test
    @DisplayName("Should pass when returned valid json for any ingredient request")
    void getIngredient() {
        String response = testDrinkJsonClient.getIngredient(testDrinkName);
        assertTrue(response.startsWith("{") && response.endsWith("}"));
        assertTrue(response.contains("ingredients"));
    }
}