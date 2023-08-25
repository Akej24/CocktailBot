package org.cocktailbot.drink.reaction.favourite;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class FavouriteService {

    private final FavouriteRepository favouriteDrinkRepository;

    void saveDrinkToFavourites(String username, String embedTitle) {
        favouriteDrinkRepository.addUserFavouriteDrink(username, embedTitle);
    }

    void removedDrinkFromFavourites(String username, String embedTitle) {
        favouriteDrinkRepository.removeUserFavouriteDrink(username, embedTitle);
    }

}
