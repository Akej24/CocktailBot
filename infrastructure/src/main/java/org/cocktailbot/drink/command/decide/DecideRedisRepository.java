package org.cocktailbot.drink.command.decide;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

class DecideRedisRepository implements DecideRepository {

    private static final String SUGGEST_PREFIX = "suggest:";
    private static final String TOTRY_PREFIX = "totry:";
    private final Jedis jedis;

    public DecideRedisRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean acceptSuggestedDrink(String username, String drinkName) {
        String from = jedis.hget(SUGGEST_PREFIX + username, drinkName);
        if(from == null || from.isBlank()) return false;
        if (jedis.scard(TOTRY_PREFIX + username) < 50) {
            Transaction transaction = jedis.multi();
            transaction.hdel(SUGGEST_PREFIX + username, drinkName);
            transaction.sadd(TOTRY_PREFIX + username, drinkName);
            transaction.exec();
            return true;
        }
        return false;
    }

    @Override
    public boolean rejectSuggestedDrink(String username, String drinkName) {
        String from = jedis.hget(SUGGEST_PREFIX + username, drinkName);
        if(from == null || from.isBlank()) return false;
        jedis.hdel(SUGGEST_PREFIX + username, drinkName);
        return true;
    }
}
