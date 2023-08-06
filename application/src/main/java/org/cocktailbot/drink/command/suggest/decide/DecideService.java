package org.cocktailbot.drink.command.suggest.decide;

class DecideService {

    private final DecideRepository decideRepository;

    public DecideService(DecideRepository decideRepository) {
        this.decideRepository = decideRepository;
    }

    public boolean acceptSuggestedDrink(String username, String drinkName) {
        return decideRepository.acceptSuggestedDrink(username, drinkName);
    }

    public boolean rejectSuggestedDrink(String username, String drinkName) {
        return decideRepository.rejectSuggestedDrink(username, drinkName);
    }
}
