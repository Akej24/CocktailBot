package org.cocktailbot.drink.command.to_try;

class ToTryService {

    private final ToTryRepository toTryRepository;

    ToTryService(ToTryRepository toTryRepository) {
        this.toTryRepository = toTryRepository;
    }

    public ToTryDrinks getUserToTryDrinks(String username) {
        return ToTryDrinks.from(toTryRepository.getUserToTryDrinks(username));
    }
}
