package org.cocktailbot.drink.command.tried;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TriedConfig {

    private final PrefixValidator prefixValidator;
    private final RedisConfig redisConfig;

    public TriedCommand subscribeTriedCommand(){
        return new TriedCommand(
                prefixValidator,
                new TriedService(
                        new TriedRedisRepository(redisConfig.getJedis())
                )
        );
    }
}
