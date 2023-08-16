package org.cocktailbot.drink.reaction.favourite;

import org.cocktailbot.drink.config.RedisConfig;
import org.cocktailbot.drink.reaction.validator.CodepointValidator;

public class FavouriteReactionConfig {

    public static FavouriteReaction getInstance() {
        return new FavouriteReaction(
                CodepointValidator.getInstance(),
                new FavouriteService(
                        new FavouriteRedisRepository(RedisConfig.getInstance().getJedis())
                )
        );
    }
}
