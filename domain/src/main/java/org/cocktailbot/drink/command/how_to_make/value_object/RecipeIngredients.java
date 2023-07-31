package org.cocktailbot.drink.command.how_to_make.value_object;

import org.cocktailbot.drink.command.shared.value_object.IngredientName;

import java.util.Map;

public record RecipeIngredients(Map<IngredientName, Measure> ingredients) { }
