package org.cocktailbot.drink.command.random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

class RandomDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!random";
    private final RandomDrinkService randomDrinkService;
    private final Validator validator;

    public RandomDrinkCommand(Validator validator, RandomDrinkService randomCocktailService) {
        this.validator = validator;
        this.randomDrinkService = randomCocktailService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            event.getChannel().sendTyping().queue();
            String author = event.getAuthor().getAsMention();
            String message = event.getMessage().getContentRaw();
            String drink = getRandomDrinkWithAlcoholContent(message);
            event.getChannel().sendMessage(buildReturnMessage(author, drink)).queue();
        }
    }

    private String getRandomDrinkWithAlcoholContent(String message) {
        return Arrays.stream(AlcoholContent.values())
                .filter(alcoholContent -> message.contains(alcoholContent.getFlag()))
                .findFirst()
                .map(randomDrinkService::getRandomDrink)
                .orElseGet(() -> randomDrinkService.getRandomDrink(AlcoholContent.ANY));
    }

    private String buildReturnMessage(String author, String drink) {
        return String.format("Hello %s!\n%s", author, drink.isEmpty()
                ? "Your drink does not exist"
                : drink.contains("Exceeded")
                ? drink
                : "This is your random drink: " + drink
        );
    }
}
