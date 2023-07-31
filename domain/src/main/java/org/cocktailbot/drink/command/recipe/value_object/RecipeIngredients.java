package org.cocktailbot.drink.command.recipe.value_object;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;

import java.util.Map;

public record RecipeIngredients(Map<IngredientName, Measure> ingredients) { }
