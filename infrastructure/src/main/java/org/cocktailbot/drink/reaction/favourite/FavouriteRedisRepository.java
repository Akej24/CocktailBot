package org.cocktailbot.drink.reaction.favourite;

import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

class FavouriteRedisRepository implements FavouriteRepository {

    private static FavouriteRedisRepository INSTANCE;
    Jedis jedis = RedisConfig.getInstance().getJedis();

    private FavouriteRedisRepository() {
    }

    public static FavouriteRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new FavouriteRedisRepository() : INSTANCE;
    }

    @Override
    public void addUserFavouriteDrink(String username, String drinkToSave) {
        Transaction transaction = jedis.multi();
        if(jedis.smembers("favourite:" + username).size() >= 50) {
            transaction.discard();
        } else {
            transaction.sadd("favourite:" + username, drinkToSave);
            transaction.sort("favourite:" + username);
            transaction.exec();
        }
    }

    @Override
    public void removeUserFavouriteDrink(String username, String drinkToRemove) {
        Transaction transaction = jedis.multi();
        transaction.srem("favourite:" + username, drinkToRemove);
        transaction.sort("favourite:" + username);
        transaction.exec();
    }
}
