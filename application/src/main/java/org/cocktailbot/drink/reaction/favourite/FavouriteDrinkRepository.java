package org.cocktailbot.drink.reaction.favourite;

interface FavouriteDrinkRepository {

    void addUserFavouriteDrink(String user, String drinkToSave);

    void removeUserFavouriteDrink(String user, String drinkToRemove);

}
