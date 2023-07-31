package org.cocktailbot.drink.command.how_to_make;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class HowToMakeDrinkConfig {

    public static HowToMakeDrinkCommand getInstance(){
        return new HowToMakeDrinkCommand(
                PrefixValidator.getInstance(),
                new HowToMakeDrinkService(
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
