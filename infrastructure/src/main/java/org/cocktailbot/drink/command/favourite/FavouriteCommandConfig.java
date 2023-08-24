package org.cocktailbot.drink.command.favourite;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FavouriteCommandConfig {

    private final PrefixValidator prefixValidator;
    private final RedisConfig redisConfig;

    public FavouriteCommand subscribeFavouriteCommand() {
        return new FavouriteCommand(
                prefixValidator,
                new FavouriteService(
                        new FavouriteRedisRepository(redisConfig.getJedis())
                )
        );
    }
}
