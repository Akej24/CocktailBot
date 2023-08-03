package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;

record Recipe(DrinkName drinkName, Instruction instruction, RecipeIngredients recipeIngredients, DrinkImageUrl drinkImageUrl) {

    public static Recipe from(
            DrinkName drinkName,
            Instruction drinkInstructions,
            RecipeIngredients drinkRecipeIngredients,
            DrinkImageUrl drinkImageUrl
    ) {
        return new Recipe(
                drinkName,
                drinkInstructions,
                drinkRecipeIngredients,
                drinkImageUrl
        );
    }
}
