package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Map;

record ToTryDrinks(Map<DrinkName, DrinkTried> drinks) {

    static ToTryDrinks from(Map<DrinkName, DrinkTried> toTry) {
        return new ToTryDrinks(toTry);
    }
}
