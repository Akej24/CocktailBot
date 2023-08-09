package org.cocktailbot.drink.command.tried;

import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

class TriedRedisRepository implements TriedRepository{

    private static TriedRedisRepository INSTANCE;
    private static final String TOTRY_PREFIX = "totry:";
    private final Jedis jedis = RedisConfig.getInstance().getJedis();

    private TriedRedisRepository() {
    }

    public static TriedRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new TriedRedisRepository() : INSTANCE;
    }

    @Override
    public boolean removeTriedDrink(String username, String drinkName) {
        boolean drinkExists = jedis.sismember(TOTRY_PREFIX + username, drinkName);
        if(!drinkExists) return false;
        jedis.srem(TOTRY_PREFIX + username, drinkName);
        return true;
    }
}
