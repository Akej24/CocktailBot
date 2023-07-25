package org.cocktailbot.core;

import org.json.JSONArray;
import org.json.JSONObject;

class RandomDrinkService {

    private final UrlResponseReader urlResponseReader;

    public RandomDrinkService(UrlResponseReader urlResponseReader) {
        this.urlResponseReader = urlResponseReader;
    }

    public String getCocktailNameJsonResponse() {
        String randomCocktailJson = urlResponseReader.getResponseFromUrl(
                "https://www.thecocktaildb.com/api/json/v1/1/random.php", "GET"
        );
        return getCocktailNameFromJson(randomCocktailJson);
    }

    private static String getCocktailNameFromJson(String randomCocktailJsonResponse) {
        JSONObject jsonObject = new JSONObject(randomCocktailJsonResponse);
        JSONArray drinksArray = jsonObject.getJSONArray("drinks");
        if (drinksArray.length() > 0) {
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString("strDrink");
        }
        return "not found";
    }
}
