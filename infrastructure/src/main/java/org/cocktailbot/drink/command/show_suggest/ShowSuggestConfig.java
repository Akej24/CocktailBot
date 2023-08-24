package org.cocktailbot.drink.command.show_suggest;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ShowSuggestConfig {

    private final PrefixValidator prefixValidator;
    private final RedisConfig redisConfig;

    public ShowSuggestCommand subscribeShowSuggestedCommand(){
        return new ShowSuggestCommand(
                prefixValidator,
                new ShowSuggestService(
                        new ShowSuggestRedisRepository(redisConfig.getJedis())
                )
        );
    }
}
