package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    @DisplayName("Should pass when the value from static fabric method is the same as value of constructor")
    void createRecipeByConstructor_and_createRecipeByStaticFabricMethod_and_checkResults() throws MalformedURLException {
        var testDrinkName = new DrinkName("test-drink-name");
        var testInstruction = new Instruction("test-instruction");
        var testIngredientName = new IngredientName("test-ingredient-name");
        var testMeasure = new Measure("test-measure");
        var testRecipeIngredients = new RecipeIngredients(Map.of(testIngredientName, testMeasure));
        var testDrinkImageUrl = new DrinkImageUrl(new URL("https://test-drink-image-url"));

        var actual = new Recipe(testDrinkName, testInstruction, testRecipeIngredients, testDrinkImageUrl);
        var expected = Recipe.from(testDrinkName, testInstruction, testRecipeIngredients, testDrinkImageUrl);

        assertEquals(expected.drinkName(), actual.drinkName());
        assertEquals(expected.instruction(), actual.instruction());
        assertEquals(expected.recipeIngredients().ingredients().size(), actual.recipeIngredients().ingredients().size());
        assertEquals(expected.drinkImageUrl(), actual.drinkImageUrl());
    }
}