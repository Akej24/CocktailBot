package org.cocktailbot.drink.drink_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DrinkJsonResponseReader implements DrinkResponseReader {

    @Override
    public String getValueFromDrink(String text, String key) {
        return getValueFromParent(text, "drinks", key);
    }

    @Override
    public String getValueFromIngredient(String text, String key) {
        return getValueFromParent(text, "ingredients", key);
    }

    private String getValueFromParent(String text, String parent, String key) {
        try {
            JSONObject json = new JSONObject(text);
            JSONArray drinksArray = json.getJSONArray(parent);
            JSONObject drink = drinksArray.getJSONObject(0);
            return drink.getString(key);
        } catch (JSONException e) {
            return "";
        }
    }
}
