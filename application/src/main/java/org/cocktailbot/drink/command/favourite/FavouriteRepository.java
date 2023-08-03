package org.cocktailbot.drink.command.favourite;

import java.util.Set;

interface FavouriteRepository {

    Set<Favourite> getUserFavouriteDrinks(String username);

}
