package org.cocktailbot.drink.command.suggest;

import redis.clients.jedis.Jedis;

class SuggestRedisRepository implements SuggestRepository {

    private static final String PREFIX = "suggest:";
    private final Jedis jedis;

    public SuggestRedisRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean saveDrinkToSuggestedUsername(String from, String drinkName, String to) {
        if (jedis.hlen(PREFIX + to) < 50) {
            jedis.hset(PREFIX + to, drinkName, from);
            return true;
        }
        return false;
    }
}
