package org.cocktailbot.drink.reaction.favourite;

class FavouriteDrinkService {

    private final FavouriteDrinkRepository favouriteDrinkRepository;

    public FavouriteDrinkService(FavouriteDrinkRepository favouriteDrinkRepository) {
        this.favouriteDrinkRepository = favouriteDrinkRepository;
    }

    void saveDrinkToFavourites(String username, String embedTitle) {
        favouriteDrinkRepository.addUserFavouriteDrink(username, embedTitle);
    }

    void removedDrinkFromFavourites(String username, String embedTitle) {
        favouriteDrinkRepository.removeUserFavouriteDrink(username, embedTitle);
    }

}
