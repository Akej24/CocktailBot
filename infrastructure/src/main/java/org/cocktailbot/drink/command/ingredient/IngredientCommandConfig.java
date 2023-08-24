package org.cocktailbot.drink.command.ingredient;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class IngredientCommandConfig {

    private final PrefixValidator prefixValidator;
    private final DrinkJsonClient drinkJsonClient;
    private final DrinkJsonResponseReader drinkJsonResponseReader;

    public IngredientCommand subscribeIngredientCommand() {
        return new IngredientCommand(
                prefixValidator,
                new IngredientService(drinkJsonClient, drinkJsonResponseReader)
        );
    }
}
