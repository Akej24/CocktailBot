package org.cocktailbot.drink.command.howtomake;

import org.cocktailbot.drink.drinkapi.DrinkClient;
import org.cocktailbot.drink.drinkapi.DrinkResponseReader;

class HowToMakeDrinkService {

    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    public HowToMakeDrinkService(DrinkClient drinkClient, DrinkResponseReader drinkResponseReader) {
        this.drinkClient = drinkClient;
        this.drinkResponseReader = drinkResponseReader;
    }

    public String getDrinkRecipe(String drinkNameFromMessage) {
        String drink = drinkClient.getDrink(drinkNameFromMessage);
        return drinkResponseReader.getValueFromDrink(drink, "strInstructions");
    }
}
