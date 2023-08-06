package org.cocktailbot.drink.command.suggest.decide;

interface DecideRepository {

    boolean acceptSuggestedDrink(String username, String drinkName);

    boolean rejectSuggestedDrink(String username, String drinkName);

}
