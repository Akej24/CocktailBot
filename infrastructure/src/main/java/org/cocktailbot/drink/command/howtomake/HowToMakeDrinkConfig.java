package org.cocktailbot.drink.command.howtomake;

import org.cocktailbot.drink.url_response.UrlJsonResponseReader;
import org.cocktailbot.drink.validator.EqualsValidator;

public class HowToMakeDrinkConfig {

    public static HowToMakeDrinkCommand getInstance(){
        return new HowToMakeDrinkCommand(
                EqualsValidator.getInstance(),
                new HowToMakeDrinkService(UrlJsonResponseReader.getInstance())
        );
    }
}
