package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.url_response.UrlJsonResponseReader;
import org.cocktailbot.drink.validator.PrefixValidator;

public class RandomDrinkConfig {

    public static RandomDrinkCommand getInstance() {
        return new RandomDrinkCommand(
                PrefixValidator.getInstance(),
                new RandomDrinkService(UrlJsonResponseReader.getInstance())
        );
    }
}
