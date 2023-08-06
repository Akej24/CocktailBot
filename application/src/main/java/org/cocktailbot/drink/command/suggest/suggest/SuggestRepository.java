package org.cocktailbot.drink.command.suggest.suggest;

interface SuggestRepository {

    boolean saveDrinkToSuggestedUsername(String from, String drinkName, String to);

}
