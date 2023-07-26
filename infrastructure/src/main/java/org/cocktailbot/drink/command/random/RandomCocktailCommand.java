package org.cocktailbot.drink.command.random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

class RandomCocktailCommand extends ListenerAdapter {

    private static final String COMMAND = "!random";
    private final RandomDrinkService randomCocktailService;
    private final Validator validator;

    public RandomCocktailCommand(Validator validator, RandomDrinkService randomCocktailService) {
        this.validator = validator;
        this.randomCocktailService = randomCocktailService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String author = event.getAuthor().getName();
            String drink = randomCocktailService.getRandomDrink();
            String message = String.format(
                    "Hello @%s!\n%s",
                    author,
                    (drink.isEmpty() ? "Your drink does not exist" : "This is your random drink: " + drink)
            );
            event.getChannel().sendMessage(message)./*addFile(new File("")).*/queue();
        }
    }


}
