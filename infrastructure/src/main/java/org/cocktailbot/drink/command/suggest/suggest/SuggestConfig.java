package org.cocktailbot.drink.command.suggest.suggest;

import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;

public class SuggestConfig {

    public static SuggestCommand getInstance(){
        return new SuggestCommand(
                PrefixValidator.getInstance(),
                new SuggestService(
                        SuggestRedisRepository.getInstance(),
                        DrinkJsonClient.getInstance(),
                        DrinkJsonResponseReader.getInstance()
                )
        );
    }
}
