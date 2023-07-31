package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.command.recipe.value_object.*;
import org.cocktailbot.drink.command.random.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;

record DrinkRecipe (DrinkName drinkName, Instruction instruction, RecipeIngredients recipeIngredients, DrinkImageUrl drinkImageUrl) {

    public static DrinkRecipe from(
            DrinkName drinkName,
            Instruction drinkInstructions,
            RecipeIngredients drinkRecipeIngredients,
            DrinkImageUrl drinkImageUrl
    ) {
        return new DrinkRecipe(
                drinkName,
                drinkInstructions,
                drinkRecipeIngredients,
                drinkImageUrl
        );
    }
}
