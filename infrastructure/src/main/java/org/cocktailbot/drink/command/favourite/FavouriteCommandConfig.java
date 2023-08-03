package org.cocktailbot.drink.command.favourite;

import org.cocktailbot.drink.validator.PrefixValidator;

public class FavouriteCommandConfig {

    public static FavouriteCommand getInstance() {
        return new FavouriteCommand(
                PrefixValidator.getInstance(),
                new FavouriteService(
                        FavouriteRedisRepository.getInstance()
                )
        );
    }
}
