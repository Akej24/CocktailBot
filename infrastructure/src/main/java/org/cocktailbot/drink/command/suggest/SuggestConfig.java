package org.cocktailbot.drink.command.suggest;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.config.RedisConfig;
import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SuggestConfig {

    private final PrefixValidator prefixValidator;
    private final DrinkJsonClient drinkJsonClient;
    private final DrinkJsonResponseReader drinkJsonResponseReader;
    private final RedisConfig redisConfig;

    public SuggestCommand subscribeSuggestCommand(){
        return new SuggestCommand(
                prefixValidator,
                new SuggestService(
                        new SuggestRedisRepository(redisConfig.getJedis()),
                        drinkJsonClient, drinkJsonResponseReader)
        );
    }
}
