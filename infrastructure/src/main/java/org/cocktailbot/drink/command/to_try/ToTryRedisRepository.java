package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.stream.Collectors;

class ToTryRedisRepository implements ToTryRepository {

    private static final String PREFIX = "totry:";
    private final Jedis jedis;

    public ToTryRedisRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Set<DrinkName> getUserToTryDrinks(String username) {
        Set<String> toTryDrinks = jedis.smembers(PREFIX + username);
        return toTryDrinks.stream()
                .map(DrinkName::new)
                .collect(Collectors.toSet());
    }
}
