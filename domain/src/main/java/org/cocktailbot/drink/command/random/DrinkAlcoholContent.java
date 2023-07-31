package org.cocktailbot.drink.command.random;

enum DrinkAlcoholContent {

    ALCOHOLIC("-a", "alcoholic"),
    NON_ALCOHOLIC("-na", "non alcoholic"),
    ANY("-all", "any");

    private final String flag;
    private final String name;

    DrinkAlcoholContent(String flag, String name) {
        this.flag = flag;
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public String getName() {
        return name;
    }

}
