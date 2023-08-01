package org.cocktailbot.drink.config;

import redis.clients.jedis.Jedis;

public class RedisTemplate {

    private static RedisTemplate INSTANCE;
    private final Jedis jedis = new Jedis("localhost", 6379);

    private RedisTemplate() {
    }

    public static RedisTemplate getInstance() {
        return INSTANCE == null ? INSTANCE = new RedisTemplate() : INSTANCE;
    }

    public Jedis getJedis() {
        return jedis;
    }
}

