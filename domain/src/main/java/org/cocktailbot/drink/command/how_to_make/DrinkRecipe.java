package org.cocktailbot.drink.command.how_to_make;

import org.cocktailbot.drink.command.how_to_make.value_object.*;
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
