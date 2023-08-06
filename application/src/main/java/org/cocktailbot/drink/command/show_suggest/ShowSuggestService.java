package org.cocktailbot.drink.command.show_suggest;

class ShowSuggestService {

    private final ShowSuggestedRepository showSuggestedRepository;

    public ShowSuggestService(ShowSuggestedRepository showSuggestedRepository) {
        this.showSuggestedRepository = showSuggestedRepository;
    }

    public SuggestedDrinks getSuggestedDrinksForUsername(String username) {
        return SuggestedDrinks.from(showSuggestedRepository.getSuggestedDrinksForUsername(username));
    }
}
