package org.cocktailbot.drink.command.suggest;

interface SuggestRepository {

    boolean saveDrinkToSuggestedUsername(String from, String drinkName, String to);

}
