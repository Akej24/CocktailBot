package org.cocktailbot.drink.command.tried;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class TriedService {

    private final TriedRepository triedRepository;

    public boolean removeTriedDrink(String username, String drinkName) {
        return triedRepository.removeTriedDrink(username, drinkName);
    }
}
