package org.cocktailbot.drink.command.favourite;

import org.cocktailbot.drink.command.favourite.value_object.Favourite;

import java.util.Set;

interface FavouriteDrinkRepository {

    Set<Favourite> getUserFavouriteDrinks(String username);

}
