package org.cocktailbot.drink.command.favourite;

import org.cocktailbot.drink.validator.PrefixValidator;

public class FavouriteDrinkConfig {
    public static FavouriteDrinkCommand getInstance() {
        return new FavouriteDrinkCommand(
                PrefixValidator.getInstance(),
                new FavouriteDrinkService(
                        FavouriteDrinkRedisRepository.getInstance()
                )
        );
    }
}
