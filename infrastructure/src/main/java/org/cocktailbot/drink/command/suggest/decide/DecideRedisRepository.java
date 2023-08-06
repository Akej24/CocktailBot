package org.cocktailbot.drink.command.suggest.decide;

import org.cocktailbot.drink.command.suggest.DecideRepository;
import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

class DecideRedisRepository implements DecideRepository {

    private static DecideRedisRepository INSTANCE;
    private static final String SUGGEST_PREFIX = "suggest:";
    private static final String TOTRY_PREFIX = "totry:";
    Jedis jedis = RedisConfig.getInstance().getJedis();

    private DecideRedisRepository() {
    }

    public static DecideRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new DecideRedisRepository() : INSTANCE;
    }

    @Override
    public boolean acceptSuggestedDrink(String username, String drinkName) {
        String from = jedis.hget(SUGGEST_PREFIX + username, drinkName);
        jedis.hdel(SUGGEST_PREFIX + username, drinkName);
        jedis.hset(TOTRY_PREFIX + username, drinkName, from);
        return true;
    }

    @Override
    public boolean rejectSuggestedDrink(String username, String drinkName) {
        jedis.hdel(SUGGEST_PREFIX + username, drinkName);
        return true;
    }
}
