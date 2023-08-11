package org.cocktailbot.drink.command.ingredient;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    @Test
    @DisplayName("Should pass when the value from static fabric method is the same as value of constructor")
    void createRecipeByConstructor_and_createRecipeByStaticFabricMethod_and_checkResults() {
        var testIngredientName = new IngredientName("test-ingredient-name");
        var testFact = List.of("test-fact");
        var testIngredientFacts = new IngredientFacts(testFact);
        var testIngredientType = new IngredientType("test-ingredient-type");
        var testAlcoholContent = AlcoholContent.ALCOHOLIC;

        var actual = new Ingredient(testIngredientName, testIngredientFacts, testIngredientType, testAlcoholContent);
        var expected = Ingredient.from(testIngredientName, testIngredientFacts, testIngredientType, testAlcoholContent);

        assertEquals(expected.ingredientName(), actual.ingredientName());
        assertEquals(expected.ingredientFacts().facts().get(0), actual.ingredientFacts().facts().get(0));
        assertEquals(expected.ingredientType(), actual.ingredientType());
        assertEquals(expected.alcoholContent(), actual.alcoholContent());
    }
}