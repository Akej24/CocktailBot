package org.cocktailbot.drink.command.suggest.suggest;

import org.cocktailbot.drink.command.suggest.SuggestRepository;
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
    public void saveSuggestedDrinkToUser(String from, String drinkName, String to) {
        jedis.hset(PREFIX + to, drinkName, from);
    }
}
