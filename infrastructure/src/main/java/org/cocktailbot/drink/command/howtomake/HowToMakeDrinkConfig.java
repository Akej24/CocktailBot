package org.cocktailbot.drink.command.howtomake;

import org.cocktailbot.drink.url_response.UrlJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class HowToMakeDrinkConfig {

    public static HowToMakeDrinkCommand getInstance(){
        return new HowToMakeDrinkCommand(
                PrefixValidator.getInstance(),
                new HowToMakeDrinkService(UrlJsonResponseReader.getInstance())
        );
    }
}
