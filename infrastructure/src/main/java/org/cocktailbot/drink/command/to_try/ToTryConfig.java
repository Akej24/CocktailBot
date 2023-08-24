package org.cocktailbot.drink.command.to_try;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ToTryConfig {

    private final PrefixValidator prefixValidator;
    private final RedisConfig redisConfig;

    public ToTryCommand subscribeToTryCommand(){
        return new ToTryCommand(
                prefixValidator,
                new ToTryService(
                        new ToTryRedisRepository(redisConfig.getJedis())
                )
        );
    }
}
