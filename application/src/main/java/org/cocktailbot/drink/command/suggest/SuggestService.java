package org.cocktailbot.drink.command.suggest;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

@AllArgsConstructor
class SuggestService {

    private final SuggestRepository suggestRepository;
    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    boolean tryAddSuggestedDrink(String author, SuggestCommandParams params) {
        if(!validateParams(params)) return false;
        if(!checkDrinkExists(params.drinkName())) return false;
        return suggestRepository.saveDrinkToSuggestedUsername(author, params.drinkName(), params.suggestedUsername());
    }

    private boolean checkDrinkExists(String drinkName) {
        String response = drinkClient.getDrink(drinkName);
        return !drinkResponseReader.getValueFromDrink(response, "strDrink").isEmpty();
    }

    private boolean validateParams(SuggestCommandParams params) {
        return !params.suggestedUsername().isEmpty()
                && !params.drinkName().isEmpty();
    }
}
