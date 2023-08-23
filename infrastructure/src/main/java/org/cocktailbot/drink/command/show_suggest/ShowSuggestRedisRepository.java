package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.stream.Collectors;

class ShowSuggestRedisRepository implements ShowSuggestedRepository {

    private static final String PREFIX = "suggest:";
    private final Jedis jedis;

    public ShowSuggestRedisRepository(Jedis jedis) {
        this.jedis = jedis;
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
