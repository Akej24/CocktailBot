package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.url_response.UrlJsonResponseReader;
import org.cocktailbot.drink.validator.EqualsValidator;

public class RandomCocktailConfig {

    public static RandomCocktailCommand getInstance() {
        return new RandomCocktailCommand(
                EqualsValidator.getInstance(),
                new RandomDrinkService(UrlJsonResponseReader.getInstance())
        );
    }
}
