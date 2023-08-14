package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RecipeServiceTest {

    private static final String testDrinkNameFromMessage = "test-drink-name-from-message";
    private static final String testBlankDrinkNameFromMessage = "";

    private DrinkClient testDrinkClient;
    private DrinkResponseReader testDrinkResponseReader;
    private RecipeService testRecipeService;

    @BeforeEach
    void setUp() {
        testDrinkClient = mock(DrinkClient.class);
        testDrinkResponseReader = mock(DrinkResponseReader.class);
        testRecipeService = new RecipeService(testDrinkClient, testDrinkResponseReader);
    }

    @Test
    @DisplayName("Should pass when successfully created recipe from mocked data")
    void getDrinkRecipe() {
        when(testDrinkClient.getDrink(testDrinkNameFromMessage))
                .thenReturn("response");
        when(testDrinkResponseReader.getValueFromDrink("response", "strDrink"))
                .thenReturn("drink-name");
        when(testDrinkResponseReader.getValueFromDrink("response", "strInstructions"))
                .thenReturn("instructions");
        when(testDrinkResponseReader.getValueFromDrink("response", "strDrinkThumb"))
                .thenReturn("https://drink.jpg");
        when(testDrinkResponseReader.getValueFromDrink("response", "strIngredient1"))
                .thenReturn("ingredient-one");
        when(testDrinkResponseReader.getValueFromDrink("response", "strIngredient2"))
                .thenReturn("ingredient-two");
        when(testDrinkResponseReader.getValueFromDrink("response", "strMeasure1"))
                .thenReturn("measure-one");
        when(testDrinkResponseReader.getValueFromDrink("response", "strMeasure2"))
                .thenReturn("measure-two");

        Recipe actual = testRecipeService.getDrinkRecipe(testDrinkNameFromMessage);
        verify(testDrinkClient, times(1)).getDrink(testDrinkNameFromMessage);
        assertEquals("drink-name", actual.drinkName().name());
        assertEquals("instructions", actual.instruction().instruction());
        assertEquals("https://drink.jpg", actual.drinkImageUrl().url().toString());
        assertEquals(2, actual.recipeIngredients().ingredients().size());
        assertEquals("measure-two", actual.recipeIngredients().ingredients().get(new IngredientName("ingredient-two")).measure());
    }

    @Test
    @DisplayName("Should pass when returned empty drink for blank message")
    void getDrinkRecipe_withBlankMessage() {
        Recipe actual = testRecipeService.getDrinkRecipe(testBlankDrinkNameFromMessage);
        verify(testDrinkClient, times(0)).getDrink(testBlankDrinkNameFromMessage);
        assertTrue(actual.drinkName().name().isEmpty());
        assertTrue(actual.instruction().instruction().isEmpty());
        assertTrue(actual.recipeIngredients().ingredients().isEmpty());
        assertNull(actual.drinkImageUrl().url());
    }

    @Test
    @DisplayName("Should pass when returned empty recipe for malformed url drink")
    void getDrinkRecipe_withMalformedURL() {
        when(testDrinkClient.getDrink(testDrinkNameFromMessage))
                .thenReturn("response");
        when(testDrinkResponseReader.getValueFromDrink("response", "strDrink"))
                .thenReturn("drink-name");
        when(testDrinkResponseReader.getValueFromDrink("response", "strInstructions"))
                .thenReturn("instructions");
        when(testDrinkResponseReader.getValueFromDrink("response", "strDrinkThumb"))
                .thenReturn("malformed-url");
        when(testDrinkResponseReader.getValueFromDrink("response", "strIngredient1"))
                .thenReturn("ingredient-one");
        when(testDrinkResponseReader.getValueFromDrink("response", "strMeasure1"))
                .thenReturn("measure-one");
        Recipe actual = testRecipeService.getDrinkRecipe(testDrinkNameFromMessage);
        verify(testDrinkClient, times(1)).getDrink(testDrinkNameFromMessage);
        assertTrue(actual.drinkName().name().isEmpty());
        assertTrue(actual.instruction().instruction().isEmpty());
        assertTrue(actual.recipeIngredients().ingredients().isEmpty());
        assertNull(actual.drinkImageUrl().url());
    }
}
