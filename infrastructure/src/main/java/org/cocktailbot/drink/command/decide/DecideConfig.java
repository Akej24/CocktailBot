package org.cocktailbot.drink.command.decide;

import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;

public class DecideConfig {

    public static DecideCommand getInstance(){
        return new DecideCommand(
                PrefixValidator.getInstance(),
                new DecideService(
                        new DecideRedisRepository(RedisConfig.getInstance().getJedis())
                )
        );
    }
}
