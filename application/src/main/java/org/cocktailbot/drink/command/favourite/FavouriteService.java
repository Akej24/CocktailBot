package org.cocktailbot.drink.command.favourite;

class FavouriteService {

    private final FavouriteRepository favouriteDrinkRepository;

    public FavouriteService(FavouriteRepository favouriteDrinkRepository) {
        this.favouriteDrinkRepository = favouriteDrinkRepository;
    }

    Favourites getUserFavouritesDrink(String username) {
        return new Favourites(favouriteDrinkRepository.getUserFavouriteDrinks(username));
    }
}
