package org.cocktailbot.drink.drink_api;

public interface DrinkResponseReader {

    String getValueFromDrink(String text, String key);

    String getValueFromIngredient(String text, String key);

}
