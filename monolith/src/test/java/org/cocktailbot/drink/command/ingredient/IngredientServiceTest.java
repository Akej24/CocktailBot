package org.cocktailbot.drink.command.ingredient;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceTest {

    private static final String testIngredientMessageName = "test-ingredient-message-name";
    private static final String testBlankIngredientMessageName = "";

    private DrinkClient testDrinkClient;
    private DrinkResponseReader testDrinkResponseReader;
    private IngredientService testIngredientService;

    @BeforeEach
    void setUp() {
        testDrinkClient = mock(DrinkClient.class);
        testDrinkResponseReader = mock(DrinkResponseReader.class);
        testIngredientService = new IngredientService(testDrinkClient, testDrinkResponseReader);
    }

    @Test
    @DisplayName("Should pass when successfully created ingredient from mocked data")
    void getIngredient() {
        when(testDrinkClient.getIngredient(testIngredientMessageName))
                .thenReturn("response");
        when(testDrinkResponseReader.getValueFromIngredient("response", "strIngredient"))
                .thenReturn("name");
        when(testDrinkResponseReader.getValueFromIngredient("response", "strDescription"))
                .thenReturn("Fact1\r\n\r\nFact2");
        when(testDrinkResponseReader.getValueFromIngredient("response", "strType"))
                .thenReturn("type");
        when(testDrinkResponseReader.getValueFromIngredient("response", "strAlcohol"))
                .thenReturn("yes");
        Ingredient actual = testIngredientService.getIngredient(testIngredientMessageName);
        verify(testDrinkClient, times(1)).getIngredient(testIngredientMessageName);
        Ingredient expected = Ingredient.from(
                new IngredientName("name"),
                new IngredientFacts(List.of("Fact1", "Fact2")),
                new IngredientType("type"),
                AlcoholContent.ALCOHOLIC
        );
        assertEquals(actual.ingredientName().name(), expected.ingredientName().name());
        assertEquals(actual.ingredientFacts().facts().size(), expected.ingredientFacts().facts().size());
        assertEquals(actual.ingredientFacts().facts().get(0), expected.ingredientFacts().facts().get(0));
        assertEquals(actual.ingredientType().type(), expected.ingredientType().type());
        assertEquals(actual.alcoholContent().name(), expected.alcoholContent().name());
    }

    @Test
    @DisplayName("Should pass when returned empty ingredient for blank message")
    void getIngredient_withBlankMessage() {
        Ingredient actual = testIngredientService.getIngredient(testBlankIngredientMessageName);
        assertTrue(actual.ingredientName().name().isEmpty());
        assertEquals(1, actual.ingredientFacts().facts().size());
        assertTrue(actual.ingredientFacts().facts().get(0).isEmpty());
        assertTrue(actual.ingredientType().type().isEmpty());
        assertNull(actual.alcoholContent());
    }

}
