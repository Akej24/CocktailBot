package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;

public class RecipeCommandConfig {

    public static RecipeCommand getInstance(){
        return new RecipeCommand(
                PrefixValidator.getInstance(),
                new RecipeService(
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
