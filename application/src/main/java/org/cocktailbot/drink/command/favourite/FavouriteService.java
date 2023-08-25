package org.cocktailbot.drink.command.favourite;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class FavouriteService {

    private final FavouriteRepository favouriteDrinkRepository;

    Favourites getUserFavouritesDrink(String username) {
        return Favourites.from(favouriteDrinkRepository.getUserFavouriteDrinks(username));
    }
}
