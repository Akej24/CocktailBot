package org.cocktailbot.drink.command.tried;

interface TriedRepository {

    boolean removeTriedDrink(String username, String drinkName);

}
