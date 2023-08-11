package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class RandomDrinkTest {

    @Test
    @DisplayName("Should pass when the value from static fabric method is the same as value of constructor")
    void createRandomDrinkByConstructor_and_createRandomDrinkByStaticFabricMethod_and_checkResults() throws MalformedURLException {
        var testDrinkName = new DrinkName("test-drink-name");
        var testDrinkImageUrl = new DrinkImageUrl(new URL("https://test-drink-image-url"));

        var actual = new RandomDrink(testDrinkName, testDrinkImageUrl);
        var expected = RandomDrink.from(testDrinkName, testDrinkImageUrl);

        assertEquals(expected.drinkName(), actual.drinkName());
        assertEquals(expected.drinkImageUrl(), actual.drinkImageUrl());
    }
}