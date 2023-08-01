package org.cocktailbot.drink.reaction.favourite;

public class FavouriteDrinkConfig {

    public static FavouriteDrinkReaction getInstance() {
        return new FavouriteDrinkReaction(
                new FavouriteDrinkService(
                        FavouriteDrinkRedisRepository.getInstance()
                )
        );
    }

}
