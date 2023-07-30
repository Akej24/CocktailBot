package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.drinkapi.DrinkClient;
import org.cocktailbot.drink.drinkapi.DrinkResponseReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

class RandomDrinkService {

    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    RandomDrinkService(DrinkClient drinkClient, DrinkResponseReader drinkResponseReader) {
        this.drinkClient = drinkClient;
        this.drinkResponseReader = drinkResponseReader;
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
        String randomDrink = drinkClient.getRandomDrink();
        String alcoholContent = drinkResponseReader.getValueFromDrink(randomDrink, "strAlcoholic");
        if (wantedAlcoholContent.equals(AlcoholContent.ANY) ||
                wantedAlcoholContent.getName().equalsIgnoreCase(alcoholContent)) {
            return new RandomDrinkResponse(
                    drinkResponseReader.getValueFromDrink(randomDrink, "strDrink"),
                    new URL(drinkResponseReader.getValueFromDrink(randomDrink, "strDrinkThumb")),
                    true
            );
        }
        return null;
    }
}
