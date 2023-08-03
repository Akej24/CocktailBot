package org.cocktailbot.drink.command.favourite;

class FavouriteDrinkService {

    private final FavouriteDrinkRepository favouriteDrinkRepository;

    public FavouriteDrinkService(FavouriteDrinkRepository favouriteDrinkRepository) {
        this.favouriteDrinkRepository = favouriteDrinkRepository;
    }

    Favourites getUserFavouritesDrink(String username) {
        return new Favourites(favouriteDrinkRepository.getUserFavouriteDrinks(username));
    }
}
