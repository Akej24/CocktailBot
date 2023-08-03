package org.cocktailbot.drink.reaction.favourite;

interface FavouriteRepository {

    void addUserFavouriteDrink(String user, String drinkToSave);

    void removeUserFavouriteDrink(String user, String drinkToRemove);

}
