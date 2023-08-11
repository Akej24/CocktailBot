package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ToTryDrinksTest {

    @Test
    @DisplayName("Should pass when the value from static fabric method is the same as value of constructor")
    void createToTryDrinksByConstructor_and_createToTryDrinksByStaticFabricMethod_and_checkResults() {
        var testDrinkName = new DrinkName("test-drink-name");
        var testDrinks = Set.of(testDrinkName);

        ToTryDrinks expected = new ToTryDrinks(testDrinks);
        ToTryDrinks actual = ToTryDrinks.from(testDrinks);

        assertEquals(expected.drinks().size(), actual.drinks().size());
        assertTrue(expected.drinks().contains(testDrinkName));
        assertTrue(actual.drinks().contains(testDrinkName));
    }
}
