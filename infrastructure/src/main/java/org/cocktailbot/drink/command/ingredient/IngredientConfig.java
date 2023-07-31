package org.cocktailbot.drink.command.ingredient;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class IngredientConfig {

    public static IngredientCommand getInstance() {
        return new IngredientCommand(
                PrefixValidator.getInstance(),
                new IngredientService(
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
