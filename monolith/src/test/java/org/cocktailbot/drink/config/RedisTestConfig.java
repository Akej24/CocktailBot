package org.cocktailbot.drink.config;

import redis.clients.jedis.Jedis;

public class RedisTestConfig {

    private static RedisTestConfig INSTANCE;
    private final Jedis jedis = new Jedis("localhost", 6385);

    private RedisTestConfig() {
    }

    public static RedisTestConfig getInstance() {
        return INSTANCE == null ? INSTANCE = new RedisTestConfig() : INSTANCE;
    }

    public Jedis getJedis() {
        return jedis;
    }
}
