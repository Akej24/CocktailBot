package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.validator.PrefixValidator;

public class DecideConfig {

    public static DecideCommand getInstance(){
        return new DecideCommand(
                PrefixValidator.getInstance(),
                new DecideService(
                        DecideRedisRepository.getInstance()
                )
        );
    }
}
