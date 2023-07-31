package org.cocktailbot.drink.command.ingredient;

import org.cocktailbot.drink.command.ingredient.value_object.IngredientFacts;
import org.cocktailbot.drink.command.ingredient.value_object.IngredientType;
import org.cocktailbot.drink.command.shared.value_object.IngredientName;

record Ingredient(

        IngredientName ingredientName,
        IngredientFacts ingredientFacts,
        IngredientType ingredientType,
        IngredientAlcoholContent ingredientAlcoholContent

) {

    public static Ingredient from(
            IngredientName ingredientName,
            IngredientFacts ingredientFacts,
            IngredientType ingredientType,
            IngredientAlcoholContent ingredientAlcoholContent
    ) {
        return new Ingredient(ingredientName, ingredientFacts, ingredientType, ingredientAlcoholContent);
    }
}
