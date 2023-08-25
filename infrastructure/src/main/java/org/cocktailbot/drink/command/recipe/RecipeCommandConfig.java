package org.cocktailbot.drink.command.recipe;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RecipeCommandConfig {

    private final PrefixValidator prefixValidator;
    private final DrinkJsonClient drinkJsonClient;
    private final DrinkJsonResponseReader drinkJsonResponseReader;

    public RecipeCommand subscribeRecipeCommand(){
        var recipeService = new RecipeService(drinkJsonClient, drinkJsonResponseReader);
        return new RecipeCommand(prefixValidator, recipeService);
    }
}
