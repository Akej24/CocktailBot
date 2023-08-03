package org.cocktailbot.drink.command.favourite;

import org.cocktailbot.drink.command.favourite.value_object.Favourite;
import org.cocktailbot.drink.config.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.stream.Collectors;

class FavouriteDrinkRedisRepository implements FavouriteDrinkRepository {

    private static FavouriteDrinkRedisRepository INSTANCE;
    Jedis jedis = RedisTemplate.getInstance().getJedis();

    private FavouriteDrinkRedisRepository() {
    }

    public static FavouriteDrinkRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new FavouriteDrinkRedisRepository() : INSTANCE;
    }

    @Override
    public Set<Favourite> getUserFavouriteDrinks(String username) {
        Set<String> members = jedis.smembers("favourite:" + username);
        return members.stream().map(Favourite::new).collect(Collectors.toSet());
    }
}
