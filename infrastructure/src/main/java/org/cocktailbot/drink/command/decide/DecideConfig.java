package org.cocktailbot.drink.command.decide;

import org.cocktailbot.drink.command.validator.PrefixValidator;

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