package org.cocktailbot.drink.command.how_to_make;

import org.cocktailbot.drink.command.how_to_make.value_object.*;
import org.cocktailbot.drink.command.random.value_object.DrinkImageUrl;
import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

class HowToMakeDrinkService {

    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;

    public HowToMakeDrinkService(DrinkClient drinkClient, DrinkResponseReader drinkResponseReader) {
        this.drinkClient = drinkClient;
        this.drinkResponseReader = drinkResponseReader;
    }

    public DrinkRecipe getDrinkRecipe(String drinkNameFromMessage) {
        String drink = drinkClient.getDrink(drinkNameFromMessage);
        try {
            return DrinkRecipe.from(
                    new DrinkName(drinkResponseReader.getValueFromDrink(drink, "strDrink")),
                    new Instruction(drinkResponseReader.getValueFromDrink(drink, "strInstructions")),
                    buildDrinkIngredients(drink),
                    new DrinkImageUrl(new URL(drinkResponseReader.getValueFromDrink(drink, "strDrinkThumb")))
            );
        } catch (MalformedURLException e) {
            System.out.println("Image url for given drink is malformed");
            e.printStackTrace();
        }
        return DrinkRecipe.from(new DrinkName(""), new Instruction(""), new RecipeIngredients(null), new DrinkImageUrl(null));
    }

    private RecipeIngredients buildDrinkIngredients(String drink) {
        var ingredients = new HashMap<IngredientName, Measure>();
        int amount = 1;
        String ingredient = drinkResponseReader.getValueFromDrink(drink, "strIngredient" + amount);
        String measure = drinkResponseReader.getValueFromDrink(drink, "strMeasure" + amount);
        while(!ingredient.isEmpty() || !measure.isEmpty()) {
            ingredients.put(new IngredientName(ingredient), new Measure(measure));
            amount++;
            ingredient = drinkResponseReader.getValueFromDrink(drink, "strIngredient" + amount);
            measure = drinkResponseReader.getValueFromDrink(drink, "strMeasure" + amount);
        }
        return new RecipeIngredients(ingredients);
    }
}
