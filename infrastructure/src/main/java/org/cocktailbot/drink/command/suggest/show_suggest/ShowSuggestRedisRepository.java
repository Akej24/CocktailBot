package org.cocktailbot.drink.command.suggest.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.suggest.ShowSuggestedRepository;
import org.cocktailbot.drink.command.suggest.Username;
import org.cocktailbot.drink.config.RedisConfig;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

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
        Map<DrinkName, Username> drinksMap = new HashMap<>();
        for (Map.Entry<String, String> entry : suggestedDrinks.entrySet()) {
            DrinkName drinkName = new DrinkName(entry.getKey());
            Username drinkSuggestingUser = new Username(entry.getValue());
            drinksMap.put(drinkName, drinkSuggestingUser);
        }
        return drinksMap;
    }
}
