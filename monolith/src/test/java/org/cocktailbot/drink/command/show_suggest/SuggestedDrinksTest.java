package org.cocktailbot.drink.command.show_suggest;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.Username;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SuggestedDrinksTest {

    @Test
    @DisplayName("Should pass when the value from static fabric method is the same as value of constructor")
    void creatSuggestedDrinksByConstructor_and_createSuggestedDrinksByStaticFabricMethod_and_checkResults() {
        var testDrinkName = new DrinkName("test-drink-name");
        var testUsername = new Username("test-username");
        var testDrinks = Map.of(testDrinkName, testUsername);

        SuggestedDrinks expected = new SuggestedDrinks(testDrinks);
        SuggestedDrinks actual = SuggestedDrinks.from(testDrinks);

        assertEquals(expected.drinks().size(), actual.drinks().size());
        assertTrue(actual.drinks().containsKey(testDrinkName));
        assertTrue(expected.drinks().containsKey(testDrinkName));
    }
}