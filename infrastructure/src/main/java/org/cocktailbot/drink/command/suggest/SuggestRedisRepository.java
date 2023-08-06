package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

class SuggestRedisRepository implements SuggestRepository {

    private static SuggestRedisRepository INSTANCE;
    private static final String PREFIX = "suggest:";
    Jedis jedis = RedisConfig.getInstance().getJedis();

    private SuggestRedisRepository() {
    }

    public static SuggestRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new SuggestRedisRepository() : INSTANCE;
    }

    @Override
    public boolean saveDrinkToSuggestedUsername(String from, String drinkName, String to) {
        if(jedis.smembers(PREFIX + to).size() < 50) {
            jedis.hset(PREFIX + to, drinkName, from);
            return true;
        }
        return false;
    }
}
