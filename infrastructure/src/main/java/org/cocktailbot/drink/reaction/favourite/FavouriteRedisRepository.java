package org.cocktailbot.drink.reaction.favourite;

import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

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
    public void addUserFavouriteDrink(String username, String drinkToSave) {
        if (jedis.hlen(PREFIX + username) < 50) {
            Transaction transaction = jedis.multi();
            transaction.sadd(PREFIX + username, drinkToSave);
            transaction.sort(PREFIX + username);
            transaction.exec();
        }
    }

    @Override
    public void removeUserFavouriteDrink(String username, String drinkToRemove) {
        Transaction transaction = jedis.multi();
        transaction.srem(PREFIX + username, drinkToRemove);
        transaction.sort(PREFIX + username);
        transaction.exec();
    }
}
