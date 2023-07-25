package org.cocktailbot.core;

public class HowToMakeDrinkConfig {

    public static HowToMakeDrinkCommand getInstance(){
        return new HowToMakeDrinkCommand(
                EqualsValidator.getInstance(),
                new HowToMakeDrinkService(UrlJsonResponseReader.getInstance())
        );
    }
}
