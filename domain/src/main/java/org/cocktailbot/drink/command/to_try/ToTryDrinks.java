package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Set;

record ToTryDrinks(Set<DrinkName> drinks) {

    static ToTryDrinks from(Set<DrinkName> drinks) {
        return new ToTryDrinks(drinks);
    }
}
