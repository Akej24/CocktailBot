package org.cocktailbot.drink.reaction.favourite;

public class FavouriteReactionConfig {

    public static FavouriteReaction getInstance() {
        return new FavouriteReaction(
                new FavouriteService(
                        FavouriteRedisRepository.getInstance()
                )
        );
    }

}
