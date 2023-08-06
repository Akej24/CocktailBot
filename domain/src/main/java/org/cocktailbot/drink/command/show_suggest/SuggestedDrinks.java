package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;

import java.util.Map;

record SuggestedDrinks(Map<DrinkName, Username> drinks) {

    static SuggestedDrinks from(Map<DrinkName, Username> drinks) {
        return new SuggestedDrinks(drinks);
    }
}
