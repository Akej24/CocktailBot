package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;

record RandomDrink(DrinkName drinkName, DrinkImageUrl drinkImageUrl) {

    public static RandomDrink from(DrinkName drinkName, DrinkImageUrl drinkImageUrl) {
        return new RandomDrink(
                drinkName,
                drinkImageUrl
        );
    }

}
