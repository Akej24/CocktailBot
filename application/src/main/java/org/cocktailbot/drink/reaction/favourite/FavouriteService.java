package org.cocktailbot.drink.reaction.favourite;

class FavouriteService {

    private final FavouriteRepository favouriteDrinkRepository;

    public FavouriteService(FavouriteRepository favouriteDrinkRepository) {
        this.favouriteDrinkRepository = favouriteDrinkRepository;
    }

    void saveDrinkToFavourites(String username, String embedTitle) {
        favouriteDrinkRepository.addUserFavouriteDrink(username, embedTitle);
    }

    void removedDrinkFromFavourites(String username, String embedTitle) {
        favouriteDrinkRepository.removeUserFavouriteDrink(username, embedTitle);
    }

}
