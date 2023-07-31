package org.cocktailbot.drink.command.how_to_make.value_object;

import java.util.Map;

public record Ingredients(Map<IngredientName, Measure> ingredients) { }
