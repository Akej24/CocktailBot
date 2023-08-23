package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;

public class ToTryConfig {

    public static ToTryCommand getInstance(){
        return new ToTryCommand(
                PrefixValidator.getInstance(),
                new ToTryService(
                        new ToTryRedisRepository(RedisConfig.getInstance().getJedis())
                )
        );
    }
}
