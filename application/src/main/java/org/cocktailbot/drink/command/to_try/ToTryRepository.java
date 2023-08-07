package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Set;

interface ToTryRepository {

    Set<DrinkName> getUserToTryDrinks(String username);

}
