package org.cocktailbot.drink.command.tried;

import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;

public class TriedConfig {

    public static TriedCommand getInstance(){
        return new TriedCommand(
                PrefixValidator.getInstance(),
                new TriedService(
                        new TriedRedisRepository(RedisConfig.getInstance().getJedis())
                )
        );
    }
}
