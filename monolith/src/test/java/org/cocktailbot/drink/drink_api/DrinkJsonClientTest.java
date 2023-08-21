package org.cocktailbot.drink.drink_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

class DrinkJsonClientTest {

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
        String response = testDrinkJsonClient.getDrink(anyString());
        assertTrue(response.startsWith("{") && response.endsWith("}"));
        assertTrue(response.contains("drinks"));
    }

    @Test
    @DisplayName("Should pass when returned valid json for any ingredient request")
    void getIngredient() {
        String response = testDrinkJsonClient.getIngredient(anyString());
        assertTrue(response.startsWith("{") && response.endsWith("}"));
        assertTrue(response.contains("ingredients"));
    }
}