package org.cocktailbot.drink.command.ingredient;

enum AlcoholContent {

    ALCOHOLIC("alcoholic"),
    NON_ALCOHOLIC("non alcoholic");

    private final String name;

    AlcoholContent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
