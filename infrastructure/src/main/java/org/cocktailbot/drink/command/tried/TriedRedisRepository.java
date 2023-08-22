package org.cocktailbot.drink.command.tried;

import redis.clients.jedis.Jedis;

class TriedRedisRepository implements TriedRepository{

    private static final String TOTRY_PREFIX = "totry:";
    private final Jedis jedis;

    TriedRedisRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean removeTriedDrink(String username, String drinkName) {
        boolean drinkExists = jedis.sismember(TOTRY_PREFIX + username, drinkName);
        if(!drinkExists) return false;
        jedis.srem(TOTRY_PREFIX + username, drinkName);
        return true;
    }
}
