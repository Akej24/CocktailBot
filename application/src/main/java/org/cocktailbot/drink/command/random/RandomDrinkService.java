package org.cocktailbot.drink.command.random;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
class RandomDrinkService {

    private static long delay = 100;
    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    RandomDrink getRandomDrinkFromAlcoholContentFlags(String messageWithFlags) {
        return Arrays.stream(AlcoholContent.values())
                .filter(alcoholContent -> messageWithFlags.contains(alcoholContent.getFlag()))
                .findFirst()
                .map(this::getRandomDrinkWithAlcoholContent)
                .orElseGet(() -> getRandomDrinkWithAlcoholContent(AlcoholContent.ANY));
    }

    private RandomDrink getRandomDrinkWithAlcoholContent(AlcoholContent wantedAlcoholContent) {
        int attempts = 0;
        int maxAttempts = 100;
        RandomDrink randomDrink = drawMatchingDrink(wantedAlcoholContent);
        try {
            while (attempts < maxAttempts) {
                Thread.sleep(delay);
                if (!randomDrink.drinkName().name().isEmpty()) return randomDrink;
                randomDrink = drawMatchingDrink(wantedAlcoholContent);
                attempts++;
            }
        } catch (InterruptedException e) {
            log.info("Thread is interrupted: " + Thread.currentThread().getName());
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
                log.info("Image url for given drink is malformed");
                throw new RuntimeException("Image url for given drink is malformed");
            }
        }
        return RandomDrink.from(new DrinkName(""), new DrinkImageUrl(null));
    }
}
