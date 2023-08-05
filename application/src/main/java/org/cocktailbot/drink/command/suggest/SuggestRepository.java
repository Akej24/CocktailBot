package org.cocktailbot.drink.command.suggest;

interface SuggestRepository {

    void saveSuggestedDrinkToUser(String from, String drinkName, String to);

}
