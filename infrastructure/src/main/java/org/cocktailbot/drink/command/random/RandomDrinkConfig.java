package org.cocktailbot.drink.command.random;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RandomDrinkConfig {

    private final PrefixValidator prefixValidator;
    private final DrinkJsonClient drinkJsonClient;
    private final DrinkJsonResponseReader drinkJsonResponseReader;

    public RandomDrinkCommand subscribeRandomDrinkCommand() {
        return new RandomDrinkCommand(
                prefixValidator,
                new RandomDrinkService(drinkJsonClient, drinkJsonResponseReader)
        );
    }
}
