package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Map;

interface ShowSuggestedRepository {

    Map<DrinkName, Username> getSuggestedDrinksForUsername(String username);

}
