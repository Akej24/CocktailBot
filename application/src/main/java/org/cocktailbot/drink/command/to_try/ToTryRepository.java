package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Map;

interface ToTryRepository {

    Map<DrinkName, DrinkTried> getUserToTryDrinks(String username);

}
