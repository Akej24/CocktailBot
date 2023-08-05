package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.validator.PrefixValidator;

public class ShowSuggestConfig {

    public static ShowSuggestCommand getInstance(){
        return new ShowSuggestCommand(
                PrefixValidator.getInstance(),
                new ShowSuggestService(
                        ShowSuggestRedisRepository.getInstance()
                )
        );
    }
}
