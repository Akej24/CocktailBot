package org.cocktailbot.drink.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@Getter
public class RedisTestConfig {

    private final Jedis jedis;

    public RedisTestConfig() {
        this.jedis = new Jedis(
                "localhost",
                6385
        );
    }
}
