package org.cocktailbot.drink.command.suggest.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.suggest.Username;

import java.util.Map;

interface ShowSuggestedRepository {

    Map<DrinkName, Username> getSuggestedDrinksForUsername(String username);

}
