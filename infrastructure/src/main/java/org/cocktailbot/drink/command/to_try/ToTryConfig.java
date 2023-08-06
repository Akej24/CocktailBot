package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.validator.PrefixValidator;

public class ToTryConfig {

    public static ToTryCommand getInstance(){
        return new ToTryCommand(
                PrefixValidator.getInstance(),
                new ToTryService(
                        ToTryRedisRepository.getInstance()
                )
        );
    }
}
