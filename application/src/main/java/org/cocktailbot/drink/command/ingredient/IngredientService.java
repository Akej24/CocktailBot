package org.cocktailbot.drink.command.ingredient;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;
import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;

import java.util.List;

class IngredientService {

    private final DrinkClient drinkClient;
    private final DrinkResponseReader drinkResponseReader;


    IngredientService(DrinkClient drinkClient, DrinkResponseReader drinkResponseReader) {
        this.drinkClient = drinkClient;
        this.drinkResponseReader = drinkResponseReader;
    }

    public Ingredient getIngredient(String ingredientMessageName) {
        String ingredient = drinkClient.getIngredient(ingredientMessageName);
        String name = drinkResponseReader.getValueFromIngredient(ingredient, "strIngredient");
        String description = drinkResponseReader.getValueFromIngredient(ingredient, "strDescription");
        String ingredientType = drinkResponseReader.getValueFromIngredient(ingredient, "strType");
        String alcoholContent = drinkResponseReader.getValueFromIngredient(ingredient, "strAlcohol");

        return Ingredient.from(
                new IngredientName(name),
                new IngredientFacts(breakDescriptionIntoFacts(description)),
                new IngredientType(ingredientType),
                alcoholContent.equalsIgnoreCase("yes") ? AlcoholContent.ALCOHOLIC : AlcoholContent.NON_ALCOHOLIC
        );
    }

    private List<String> breakDescriptionIntoFacts(String ingredientDescription) {
        String[] splitDescription = ingredientDescription.split("\\r\\n\\r\\n");
        return List.of(splitDescription);
    }
}
