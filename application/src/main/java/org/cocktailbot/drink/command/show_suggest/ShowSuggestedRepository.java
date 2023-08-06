package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;

import java.util.Map;

interface ShowSuggestedRepository {

    Map<DrinkName, Username> getSuggestedDrinksForUsername(String username);

}
