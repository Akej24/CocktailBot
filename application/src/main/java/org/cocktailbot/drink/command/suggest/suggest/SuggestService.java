package org.cocktailbot.drink.command.suggest.suggest;

import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

class SuggestService {

    private final SuggestRepository suggestRepository;
    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    public SuggestService(SuggestRepository suggestRepository, DrinkClient drinkClient, DrinkResponseReader drinkResponseReader) {
        this.suggestRepository = suggestRepository;
        this.drinkClient = drinkClient;
        this.drinkResponseReader = drinkResponseReader;
    }

    boolean tryAddSuggestedDrink(String from, String drinkName, String to) {
        String response = drinkClient.getDrink(drinkName);
        if(drinkResponseReader.getValueFromDrink(response, "strDrink").isEmpty()){
            return false;
        }
        suggestRepository.saveSuggestedDrinkToUser(from, drinkName, to);
        return true;
    }
}
