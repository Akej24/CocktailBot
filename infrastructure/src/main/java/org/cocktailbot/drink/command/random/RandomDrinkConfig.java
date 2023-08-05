package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class RandomDrinkConfig {

    public static RandomDrinkCommand getInstance() {
        return new RandomDrinkCommand(
                PrefixValidator.getInstance(),
                new RandomDrinkService(
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
