package org.cocktailbot.drink.drinkapi;

import org.json.JSONArray;
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
        JSONObject jsonObject = new JSONObject(text);
        JSONArray drinksArray = jsonObject.getJSONArray(parent);
        if (drinksArray.length() > 0) {
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString(key);
        }
        return "";
    }
}
