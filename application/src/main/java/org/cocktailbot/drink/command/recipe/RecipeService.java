package org.cocktailbot.drink.command.recipe;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cocktailbot.drink.command.shared.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Slf4j
@AllArgsConstructor
class RecipeService {

    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    Recipe getDrinkRecipe(String drinkNameFromMessage) {
        if (drinkNameFromMessage.isBlank()) return getEmptyRecipe();
        try {
            String drink = drinkClient.getDrink(drinkNameFromMessage);
            return Recipe.from(
                    new DrinkName(drinkResponseReader.getValueFromDrink(drink, "strDrink")),
                    new Instruction(drinkResponseReader.getValueFromDrink(drink, "strInstructions")),
                    buildDrinkIngredients(drink),
                    new DrinkImageUrl(new URL(drinkResponseReader.getValueFromDrink(drink, "strDrinkThumb")))
            );
        } catch (MalformedURLException e) {
            log.info("Image url for given drink is malformed");
            return getEmptyRecipe();
        }
    }

    private Recipe getEmptyRecipe() {
        return Recipe.from(
                new DrinkName(""),
                new Instruction(""),
                new RecipeIngredients(new HashMap<>()),
                new DrinkImageUrl(null));
    }

    private RecipeIngredients buildDrinkIngredients(String drink) {
        var ingredients = new HashMap<IngredientName, Measure>();
        int amount = 1;
        String ingredient = drinkResponseReader.getValueFromDrink(drink, "strIngredient" + amount);
        String measure = drinkResponseReader.getValueFromDrink(drink, "strMeasure" + amount);
        while(ingredient != null && measure != null && !ingredient.isEmpty() && !measure.isEmpty()) {
            ingredients.put(new IngredientName(ingredient), new Measure(measure));
            amount++;
            ingredient = drinkResponseReader.getValueFromDrink(drink, "strIngredient" + amount);
            measure = drinkResponseReader.getValueFromDrink(drink, "strMeasure" + amount);
        }
        return new RecipeIngredients(ingredients);
    }
}
