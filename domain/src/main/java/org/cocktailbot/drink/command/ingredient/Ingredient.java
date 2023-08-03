package org.cocktailbot.drink.command.ingredient;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;

record Ingredient(

        IngredientName ingredientName,
        IngredientFacts ingredientFacts,
        IngredientType ingredientType,
        AlcoholContent alcoholContent

) {

    public static Ingredient from(
            IngredientName ingredientName,
            IngredientFacts ingredientFacts,
            IngredientType ingredientType,
            AlcoholContent alcoholContent
    ) {
        return new Ingredient(ingredientName, ingredientFacts, ingredientType, alcoholContent);
    }
}
