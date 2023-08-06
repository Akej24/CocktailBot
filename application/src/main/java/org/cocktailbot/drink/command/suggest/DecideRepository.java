package org.cocktailbot.drink.command.suggest;

interface DecideRepository {

    boolean acceptSuggestedDrink(String username, String drinkName);

    boolean rejectSuggestedDrink(String username, String drinkName);

}
