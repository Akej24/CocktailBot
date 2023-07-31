package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class DrinkRecipeConfig {

    public static DrinkRecipeCommand getInstance(){
        return new DrinkRecipeCommand(
                PrefixValidator.getInstance(),
                new DrinkRecipeService(
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
