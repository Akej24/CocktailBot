package org.cocktailbot.drink.command.favourite;

import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;

public class FavouriteCommandConfig {

    public static FavouriteCommand getInstance() {
        return new FavouriteCommand(
                PrefixValidator.getInstance(),
                new FavouriteService(
                        new FavouriteRedisRepository(RedisConfig.getInstance().getJedis())
                )
        );
    }
}
