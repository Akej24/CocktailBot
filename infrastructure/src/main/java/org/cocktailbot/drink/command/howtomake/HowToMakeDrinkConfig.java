package org.cocktailbot.drink.command.howtomake;

import org.cocktailbot.drink.drinkapi.DrinkJsonClient;
import org.cocktailbot.drink.drinkapi.DrinkJsonResponseReader;
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
