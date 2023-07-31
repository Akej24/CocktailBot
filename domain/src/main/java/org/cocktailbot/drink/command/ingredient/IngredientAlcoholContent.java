package org.cocktailbot.drink.command.ingredient;

enum IngredientAlcoholContent {

    ALCOHOLIC("alcoholic"),
    NON_ALCOHOLIC("non alcoholic");

    private final String name;

    IngredientAlcoholContent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
