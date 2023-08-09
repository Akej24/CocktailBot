package org.cocktailbot.drink.command.decide;

import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

class DecideRedisRepository implements DecideRepository {

    private static DecideRedisRepository INSTANCE;
    private static final String SUGGEST_PREFIX = "suggest:";
    private static final String TOTRY_PREFIX = "totry:";
    private final Jedis jedis = RedisConfig.getInstance().getJedis();

    private DecideRedisRepository() {
    }

    public static DecideRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new DecideRedisRepository() : INSTANCE;
    }

    @Override
    public boolean acceptSuggestedDrink(String username, String drinkName) {
        String from = jedis.hget(SUGGEST_PREFIX + username, drinkName);
        if(from.isBlank()) return false;
        if (jedis.scard(TOTRY_PREFIX + username) < 50) {
            Transaction transaction = jedis.multi();
            transaction.hdel(SUGGEST_PREFIX + username, drinkName);
            transaction.sadd(TOTRY_PREFIX + username, drinkName);
            transaction.exec();
        }
        return true;
    }

    @Override
    public boolean rejectSuggestedDrink(String username, String drinkName) {
        String from = jedis.hget(SUGGEST_PREFIX + username, drinkName);
        if(from.isBlank()) return false;
        jedis.hdel(SUGGEST_PREFIX + username, drinkName);
        return true;
    }
}