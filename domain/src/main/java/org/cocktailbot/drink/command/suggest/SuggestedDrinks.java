package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Map;

public record SuggestedDrinks(Map<DrinkName, Username> drinks) {

    public static SuggestedDrinks from(Map<DrinkName, Username> drinks) {
        return new SuggestedDrinks(drinks);
    }
}
