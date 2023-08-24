package org.cocktailbot.drink.drink_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DrinkJsonClientTest {

    private static final String testDrinkName = "test-drink-name";

    @Autowired
    private DrinkJsonClient testDrinkJsonClient;

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