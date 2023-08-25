package org.cocktailbot.drink.command.decide;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class DecideService {

    private final DecideRepository decideRepository;

    public boolean acceptSuggestedDrink(String username, String drinkName) {
        return decideRepository.acceptSuggestedDrink(username, drinkName);
    }

    public boolean rejectSuggestedDrink(String username, String drinkName) {
        return decideRepository.rejectSuggestedDrink(username, drinkName);
    }
}
