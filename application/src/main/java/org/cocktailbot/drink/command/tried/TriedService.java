package org.cocktailbot.drink.command.tried;

class TriedService {

    private final TriedRepository triedRepository;

    public TriedService(TriedRepository triedRepository) {
        this.triedRepository = triedRepository;
    }

    public boolean removeTriedDrink(String username, String drinkName) {
        return triedRepository.removeTriedDrink(username, drinkName);
    }
}
