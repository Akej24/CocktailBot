package org.cocktailbot.drink.drink_api;

public interface DrinkClient {

    String getRandomDrink();

    String getDrink(String drinkName);

}
