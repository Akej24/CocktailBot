package org.cocktailbot.drink.command.favourite;

import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.stream.Collectors;

class FavouriteRedisRepository implements FavouriteRepository {

    private static FavouriteRedisRepository INSTANCE;
    private static final String PREFIX = "favourite:";
    Jedis jedis = RedisConfig.getInstance().getJedis();

    private FavouriteRedisRepository() {
    }

    public static FavouriteRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new FavouriteRedisRepository() : INSTANCE;
    }

    @Override
    public Set<Favourite> getUserFavouriteDrinks(String username) {
        Set<String> members = jedis.smembers(PREFIX + username);
        return members.stream().map(Favourite::new).collect(Collectors.toSet());
    }
}
