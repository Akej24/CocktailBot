package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.cocktailbot.drink.config.RedisConfig;

public class ShowSuggestConfig {

    public static ShowSuggestCommand getInstance(){
        return new ShowSuggestCommand(
                PrefixValidator.getInstance(),
                new ShowSuggestService(
                        new ShowSuggestRedisRepository(RedisConfig.getInstance().getJedis())
                )
        );
    }
}
