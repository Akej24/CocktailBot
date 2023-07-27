package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.url_response.UrlResponseReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

class RandomDrinkService {

    private final UrlResponseReader urlResponseReader;

    RandomDrinkService(UrlResponseReader urlResponseReader) {
        this.urlResponseReader = urlResponseReader;
    }

    RandomDrinkResponse getRandomDrinkWithAlcoholContent(String messageWithFlags) {
        return Arrays.stream(AlcoholContent.values())
                .filter(alcoholContent -> messageWithFlags.contains(alcoholContent.getFlag()))
                .findFirst()
                .map(this::getRandomDrink)
                .orElseGet(() -> getRandomDrink(AlcoholContent.ANY));
    }

    RandomDrinkResponse getRandomDrink(AlcoholContent wantedAlcoholContent) {
        int maxAttempts = 100;
        int attempts = 0;
        try {
            while (attempts < maxAttempts) {
                Thread.sleep(300);
                RandomDrinkResponse randomDrinkJson = drawMatchingDrink(wantedAlcoholContent);
                if (randomDrinkJson != null) return randomDrinkJson;
                attempts++;
            }
        } catch (InterruptedException | MalformedURLException e) {
            e.printStackTrace();
        }
        return new RandomDrinkResponse("", null, false);
    }

    private RandomDrinkResponse drawMatchingDrink(AlcoholContent wantedAlcoholContent) throws MalformedURLException {
        String randomDrinkJson = getRandomDrinkJson();
        String alcoholContent = getNodeValueFromDrinksJson(randomDrinkJson, "strAlcoholic");
        if (wantedAlcoholContent.equals(AlcoholContent.ANY) ||
                wantedAlcoholContent.getName().equalsIgnoreCase(alcoholContent)) {
            return new RandomDrinkResponse(
                    getNodeValueFromDrinksJson(randomDrinkJson, "strDrink"),
                    new URL(getNodeValueFromDrinksJson(randomDrinkJson, "strDrinkThumb")),
                    true
            );
        }
        return null;
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
