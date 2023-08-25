package org.cocktailbot.drink.command.to_try;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class ToTryService {

    private final ToTryRepository toTryRepository;

    public ToTryDrinks getUserToTryDrinks(String username) {
        return ToTryDrinks.from(toTryRepository.getUserToTryDrinks(username));
    }
}
