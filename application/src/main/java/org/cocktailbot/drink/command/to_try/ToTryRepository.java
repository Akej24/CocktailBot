package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;

import java.util.Map;

interface ToTryRepository {

    Map<DrinkName, Username> getUserToTryDrinks(String username);

}
