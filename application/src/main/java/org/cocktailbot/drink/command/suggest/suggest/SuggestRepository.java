package org.cocktailbot.drink.command.suggest.suggest;

interface SuggestRepository {

    void saveSuggestedDrinkToUser(String from, String drinkName, String to);

}
