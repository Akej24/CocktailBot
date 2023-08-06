package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;
import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.stream.Collectors;

class ShowSuggestRedisRepository implements ShowSuggestedRepository {

    private static ShowSuggestRedisRepository INSTANCE;
    private static final String PREFIX = "suggest:";
    Jedis jedis = RedisConfig.getInstance().getJedis();

    private ShowSuggestRedisRepository() {
    }

    public static ShowSuggestRedisRepository getInstance() {
        return INSTANCE == null ? INSTANCE = new ShowSuggestRedisRepository() : INSTANCE;
    }

    @Override
    public Map<DrinkName, Username> getSuggestedDrinksForUsername(String username) {
        Map<String, String> suggestedDrinks = jedis.hgetAll(PREFIX + username);
        return suggestedDrinks.entrySet()
                .stream()
                .map(entry -> {
                    DrinkName drinkName = new DrinkName(entry.getKey());
                    Username drinkSuggestingUser = new Username(entry.getValue());
                    return Map.entry(drinkName, drinkSuggestingUser);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
