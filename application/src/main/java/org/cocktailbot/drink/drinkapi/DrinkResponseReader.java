package org.cocktailbot.drink.drinkapi;

public interface DrinkResponseReader {

    String getValueFromDrink(String text, String key);

    String getValueFromIngredient(String text, String key);

}
