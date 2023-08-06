package org.cocktailbot.drink.command.suggest.suggest;

import net.dv8tion.jda.api.entities.Member;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

import java.util.List;

class SuggestService {

    private final SuggestRepository suggestRepository;
    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    public SuggestService(SuggestRepository suggestRepository, DrinkClient drinkClient, DrinkResponseReader drinkResponseReader) {
        this.suggestRepository = suggestRepository;
        this.drinkClient = drinkClient;
        this.drinkResponseReader = drinkResponseReader;
    }

    boolean tryAddSuggestedDrink(String author, SuggestCommandParams params, List<Member> availableUsers) {
        if(!checkUserExists(params.suggestedUsername(), availableUsers)) return false;
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

    private boolean checkUserExists(String suggestedUsername, List<Member> availableUsers) {
        return availableUsers.stream().anyMatch(member -> member
                .getEffectiveName().equalsIgnoreCase(suggestedUsername));
    }
}
