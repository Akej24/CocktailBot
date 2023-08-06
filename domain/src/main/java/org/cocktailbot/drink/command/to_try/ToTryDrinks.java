package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;

import java.util.Map;

record ToTryDrinks(Map<DrinkName, Username> drinks) {

    static ToTryDrinks from(Map<DrinkName, Username> toTry) {
        return new ToTryDrinks(toTry);
    }
}
