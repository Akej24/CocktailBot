package org.cocktailbot.drink.command.decide;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DecideConfig {

    private final PrefixValidator prefixValidator;
    private final RedisConfig redisConfig;

    public DecideCommand subscribeDecideCommand() {
        return new DecideCommand(
                prefixValidator,
                new DecideService(
                        new DecideRedisRepository(redisConfig.getJedis())
                )
        );
    }
}
