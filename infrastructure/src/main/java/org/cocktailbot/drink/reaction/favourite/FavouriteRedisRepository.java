package org.cocktailbot.drink.reaction.favourite;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

class FavouriteRedisRepository implements FavouriteRepository {

    private static final String PREFIX = "favourite:";
    private final Jedis jedis;

    public FavouriteRedisRepository(Jedis jedis) {
        this.jedis = jedis;
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
