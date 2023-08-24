package org.cocktailbot.drink.command.recipe;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.drink_api.DrinkJsonClient;
import org.cocktailbot.drink.drink_api.DrinkJsonResponseReader;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RecipeCommandConfig {

    private final PrefixValidator prefixValidator;
    private final DrinkJsonClient drinkJsonClient;
    private final DrinkJsonResponseReader drinkJsonResponseReader;

    public RecipeCommand subscribeRecipeCommand(){
        return new RecipeCommand(
                prefixValidator,
                new RecipeService(drinkJsonClient, drinkJsonResponseReader)
        );
    }
}
