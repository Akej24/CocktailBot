package org.cocktailbot.drink.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@Getter
public class RedisConfig {

    private final Jedis jedis;

    RedisConfig() {
        this.jedis = new Jedis(
                "localhost",
                6379
        );
    }
}

