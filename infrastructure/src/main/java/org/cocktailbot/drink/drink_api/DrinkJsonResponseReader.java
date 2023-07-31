package org.cocktailbot.drink.drink_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DrinkJsonResponseReader implements DrinkResponseReader {

    private static DrinkJsonResponseReader INSTANCE;

    private DrinkJsonResponseReader() {
    }

    public static DrinkJsonResponseReader getInstance() {
        return INSTANCE == null ? INSTANCE = new DrinkJsonResponseReader() : INSTANCE;
    }

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
