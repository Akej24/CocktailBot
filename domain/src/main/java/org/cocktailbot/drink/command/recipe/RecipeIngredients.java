package org.cocktailbot.drink.command.recipe;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;

import java.util.Map;

record RecipeIngredients(Map<IngredientName, Measure> ingredients) { }
