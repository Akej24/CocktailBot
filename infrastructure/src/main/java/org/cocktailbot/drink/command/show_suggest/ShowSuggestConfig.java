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
        var showSuggestRedisRepository = new ShowSuggestRedisRepository(redisConfig.getJedis());
        var showSuggestedService = new ShowSuggestService(showSuggestRedisRepository);
        return new ShowSuggestCommand(prefixValidator, showSuggestedService);
    }
}
