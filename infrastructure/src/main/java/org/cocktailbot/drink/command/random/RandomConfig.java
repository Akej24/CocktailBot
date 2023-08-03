package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class RandomConfig {

    public static RandomCommand getInstance() {
        return new RandomCommand(
                PrefixValidator.getInstance(),
                new RandomDrinkService(
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
