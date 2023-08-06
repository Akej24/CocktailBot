package org.cocktailbot.drink.command.suggest.decide;

class DecideService {

    private final DecideRepository decideRepository;

    public DecideService(DecideRepository decideRepository) {
        this.decideRepository = decideRepository;
    }

    public boolean acceptSuggestedDrink(String username, String drinkName) {
        decideRepository.acceptSuggestedDrink(username, drinkName);
        return true;
    }

    public boolean rejectSuggestedDrink(String username, String drinkName) {
        decideRepository.rejectSuggestedDrink(username, drinkName);
        return true;
    }
}
