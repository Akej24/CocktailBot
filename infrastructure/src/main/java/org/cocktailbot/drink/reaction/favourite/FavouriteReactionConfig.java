package org.cocktailbot.drink.reaction.favourite;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.config.RedisConfig;
import org.cocktailbot.drink.reaction.validator.CodepointValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FavouriteReactionConfig {

    private final RedisConfig redisConfig;

    public FavouriteReaction subscribeFavouriteReaction() {
        return new FavouriteReaction(
                CodepointValidator.getInstance(),
                new FavouriteService(
                        new FavouriteRedisRepository(redisConfig.getJedis())
                )
        );
    }
}
