package org.cocktailbot.drink.command.favourite;

import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.stream.Collectors;

class FavouriteRedisRepository implements FavouriteRepository {

    private static final String PREFIX = "favourite:";
    private final Jedis jedis;

    public FavouriteRedisRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Set<Favourite> getUserFavouriteDrinks(String username) {
        Set<String> members = jedis.smembers(PREFIX + username.toLowerCase());
        return members.stream()
                .map(Favourite::new)
                .collect(Collectors.toSet());
    }
}
