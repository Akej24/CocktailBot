package org.cocktailbot.drink.reaction.favourite;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.config.RedisConfig;
import org.cocktailbot.drink.reaction.validator.CodepointValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FavouriteReactionConfig {

    private final CodepointValidator codepointValidator;
    private final RedisConfig redisConfig;

    public FavouriteReaction subscribeFavouriteReaction() {
        var favouriteRedisRepository = new FavouriteRedisRepository(redisConfig.getJedis());
        var favouriteService = new FavouriteService(favouriteRedisRepository);
        return new FavouriteReaction(codepointValidator, favouriteService);
    }
}
