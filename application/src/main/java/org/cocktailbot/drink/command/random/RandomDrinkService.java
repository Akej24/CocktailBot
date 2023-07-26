package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.url_response.UrlResponseReader;
import org.json.JSONArray;
import org.json.JSONObject;

class RandomDrinkService {

    private final UrlResponseReader urlResponseReader;

    RandomDrinkService(UrlResponseReader urlResponseReader) {
        this.urlResponseReader = urlResponseReader;
    }

    String getRandomDrink(AlcoholContent wantedAlcoholContent) {
        int maxAttempts = 100;
        int attempts = 0;
        try {
            while (attempts < maxAttempts) {
                Thread.sleep(300);
                String randomDrinkJson = getRandomDrinkJson();
                String alcoholContent = getNodeValueFromDrinksJson(randomDrinkJson, "strAlcoholic");
                if (wantedAlcoholContent.equals(AlcoholContent.ANY) ||
                        wantedAlcoholContent.getName().equalsIgnoreCase(alcoholContent)){
                    return getNodeValueFromDrinksJson(randomDrinkJson, "strDrink");
                }
                attempts++;
            }
        } catch(InterruptedException e) {
            return "Exceeded the maximum number of connection attempts or something else went wrong, please try again";
        }
        return "";
    }

    private String getRandomDrinkJson() {
        return urlResponseReader.getResponseFromUrl("https://www.thecocktaildb.com/api/json/v1/1/random.php", "GET");
    }

    private static String getNodeValueFromDrinksJson(String randomCocktailJson, String node) {
        JSONObject jsonObject = new JSONObject(randomCocktailJson);
        JSONArray drinksArray = jsonObject.getJSONArray("drinks");
        if (drinksArray.length() > 0) {
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString(node);
        }
        return "";
    }
}
