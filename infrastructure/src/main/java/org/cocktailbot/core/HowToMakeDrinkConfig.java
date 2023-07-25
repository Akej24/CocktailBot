package org.cocktailbot.core;

public class HowToMakeDrinkConfig {

    public static HowToMakeDrinkCommand getInstance(){
        HowToMakeDrinkService howToMakeDrinkService = null;
        return new HowToMakeDrinkCommand(EqualsValidator.getInstance(), UrlJsonResponseReader.getInstance(), howToMakeDrinkService);
    }
}
