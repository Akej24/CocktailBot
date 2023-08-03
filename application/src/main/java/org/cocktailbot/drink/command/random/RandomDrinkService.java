package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

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

    RandomDrink getRandomDrinkWithAlcoholContent(String messageWithFlags) {
        return Arrays.stream(AlcoholContent.values())
                .filter(alcoholContent -> messageWithFlags.contains(alcoholContent.getFlag()))
                .findFirst()
                .map(this::getRandomDrink)
                .orElseGet(() -> getRandomDrink(AlcoholContent.ANY));
    }

    RandomDrink getRandomDrink(AlcoholContent wantedAlcoholContent) {
        int maxAttempts = 100;
        int attempts = 0;
        RandomDrink randomDrink = drawMatchingDrink(wantedAlcoholContent);
        try {
            while (attempts < maxAttempts) {
                Thread.sleep(300);
                if (!randomDrink.drinkName().name().isEmpty()) return randomDrink;
                randomDrink = drawMatchingDrink(wantedAlcoholContent);
                attempts++;
            }
        } catch (InterruptedException e) {
            System.out.println("Thread is interrupted: " + Thread.currentThread().getName());
            e.printStackTrace();
        }
        return randomDrink;
    }

    private RandomDrink drawMatchingDrink(AlcoholContent wantedAlcoholContent) {
        String randomDrink = drinkClient.getRandomDrink();
        String alcoholContent = drinkResponseReader.getValueFromDrink(randomDrink, "strAlcoholic");
        if (wantedAlcoholContent.equals(AlcoholContent.ANY) ||
                wantedAlcoholContent.getName().equalsIgnoreCase(alcoholContent)) {
            try {
                return RandomDrink.from(
                        new DrinkName(drinkResponseReader.getValueFromDrink(randomDrink, "strDrink")),
                        new DrinkImageUrl(new URL(drinkResponseReader.getValueFromDrink(randomDrink, "strDrinkThumb")))
                );
            } catch (MalformedURLException e) {
                System.out.println("Image url for given drink is malformed");
                e.printStackTrace();
            }
        }
        return RandomDrink.from(new DrinkName(""), new DrinkImageUrl(null));
    }
}
