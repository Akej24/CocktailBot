package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.validator.PrefixValidator;

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
