package org.cocktailbot.drink.reaction.favourite;

import org.cocktailbot.drink.config.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

class FavouriteDrinkRedisRepository implements FavouriteDrinkRepository {

    private static FavouriteDrinkRedisRepository INSTANCE;
    Jedis jedis = RedisTemplate.getInstance().getJedis();

    private FavouriteDrinkRedisRepository() {
    }

    public static FavouriteDrinkRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new FavouriteDrinkRedisRepository() : INSTANCE;
    }

    @Override
    public void addUserFavouriteDrink(String username, String drinkToSave) {
        Transaction transaction = jedis.multi();
        transaction.sadd("favourite:" + username, drinkToSave);
        transaction.sort("favourite:" + username);
        transaction.exec();
    }

    @Override
    public void removeUserFavouriteDrink(String username, String drinkToRemove) {
        Transaction transaction = jedis.multi();
        transaction.srem("favourite:" + username, drinkToRemove);
        transaction.sort("favourite:" + username);
        transaction.exec();
    }
}
