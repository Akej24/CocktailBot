package org.cocktailbot.core;

public class RandomCocktailConfig {

    public static RandomCocktailCommand getInstance() {
        return new RandomCocktailCommand(PrefixValidator.getInstance(), UrlJsonResponseReader.getInstance());
    }
}
