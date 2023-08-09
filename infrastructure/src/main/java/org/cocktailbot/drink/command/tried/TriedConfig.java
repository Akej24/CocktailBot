package org.cocktailbot.drink.command.tried;

import org.cocktailbot.drink.command.validator.PrefixValidator;

public class TriedConfig {

    public static TriedCommand getInstance(){
        return new TriedCommand(
                PrefixValidator.getInstance(),
                new TriedService(
                        TriedRedisRepository.getInstance()
                )
        );
    }
}
