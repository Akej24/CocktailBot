package org.cocktailbot.drink.command.howtomake;

import org.cocktailbot.drink.url_response.UrlResponseReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class HowToMakeDrinkService {

    private final UrlResponseReader urlResponseReader;

    public HowToMakeDrinkService(UrlResponseReader urlResponseReader) {
        this.urlResponseReader = urlResponseReader;
    }

    public String getDrinkRecipe(String drinkNameFromMessage) {
        String drinkJsonResponse = urlResponseReader.getResponseFromUrl(
                "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkNameFromMessage, "GET"
        );
        return getRecipeFromJson(drinkJsonResponse);
    }

    private static String getRecipeFromJson(String instructionsJsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(instructionsJsonResponse);
            JSONArray drinksArray = jsonObject.getJSONArray("drinks");
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString("strInstructions");
        } catch (JSONException e) {
            return "";
        }
    }
}
