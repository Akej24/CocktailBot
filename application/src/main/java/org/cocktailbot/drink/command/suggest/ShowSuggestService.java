package org.cocktailbot.drink.command.suggest;

public class ShowSuggestService {

    private final ShowSuggestedRepository showSuggestedRepository;

    public ShowSuggestService(ShowSuggestedRepository showSuggestedRepository) {
        this.showSuggestedRepository = showSuggestedRepository;
    }

    public SuggestedDrinks getSuggestedDrinksForUsername(String username) {
        return new SuggestedDrinks(showSuggestedRepository.getSuggestedDrinksForUsername(username));
    }
}
