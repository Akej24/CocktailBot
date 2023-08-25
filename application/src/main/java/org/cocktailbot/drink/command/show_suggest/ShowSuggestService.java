package org.cocktailbot.drink.command.show_suggest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ShowSuggestService {

    private final ShowSuggestedRepository showSuggestedRepository;

    public SuggestedDrinks getSuggestedDrinksForUsername(String username) {
        return SuggestedDrinks.from(showSuggestedRepository.getSuggestedDrinksForUsername(username));
    }
}
