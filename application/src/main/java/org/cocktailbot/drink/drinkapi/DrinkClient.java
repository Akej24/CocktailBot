package org.cocktailbot.drink.drinkapi;

public interface DrinkClient {

    String getRandomDrink();

    String getDrink(String drinkName);

}
