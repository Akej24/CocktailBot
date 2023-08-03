package org.cocktailbot.drink.config;

import redis.clients.jedis.Jedis;

public class RedisConfig {

    private static RedisConfig INSTANCE;
    private final Jedis jedis = new Jedis("localhost", 6379);

    private RedisConfig() {
    }

    public static RedisConfig getInstance() {
        return INSTANCE == null ? INSTANCE = new RedisConfig() : INSTANCE;
    }

    public Jedis getJedis() {
        return jedis;
    }
}

