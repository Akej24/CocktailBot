package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;

import java.util.Map;

record SuggestedDrinks(Map<DrinkName, Username> drinks) { }
