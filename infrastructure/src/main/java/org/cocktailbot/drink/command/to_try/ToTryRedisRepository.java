package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;
import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.stream.Collectors;

class ToTryRedisRepository implements ToTryRepository {

    private static ToTryRedisRepository INSTANCE;
    private static final String PREFIX = "totry:";
    Jedis jedis = RedisConfig.getInstance().getJedis();

    private ToTryRedisRepository() {
    }

    public static ToTryRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new ToTryRedisRepository() : INSTANCE;
    }

    @Override
    public Map<DrinkName, Username> getUserToTryDrinks(String username) {
        Map<String, String> toTryDrinks = jedis.hgetAll(PREFIX + username);
        return toTryDrinks.entrySet()
                .stream()
                .map(entry -> {
                    DrinkName drinkName = new DrinkName(entry.getKey());
                    Username from = new Username(entry.getValue());
                    return Map.entry(drinkName, from);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
