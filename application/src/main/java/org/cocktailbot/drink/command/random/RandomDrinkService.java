package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.url_response.UrlResponseReader;
import org.json.JSONArray;
import org.json.JSONObject;

class RandomDrinkService {

    private final UrlResponseReader urlResponseReader;

    public RandomDrinkService(UrlResponseReader urlResponseReader) {
        this.urlResponseReader = urlResponseReader;
    }

    public String getRandomDrink() {
        String randomCocktailJson = urlResponseReader.getResponseFromUrl(
                "https://www.thecocktaildb.com/api/json/v1/1/random.php", "GET"
        );
        return getDrinkNameFromJson(randomCocktailJson);
    }

    private static String getDrinkNameFromJson(String randomCocktailJsonResponse) {
        JSONObject jsonObject = new JSONObject(randomCocktailJsonResponse);
        JSONArray drinksArray = jsonObject.getJSONArray("drinks");
        if (drinksArray.length() > 0) {
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString("strDrink");
        }
        return "";
    }
}
