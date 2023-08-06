package org.cocktailbot.drink.command.random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import static org.cocktailbot.drink.utils.UrlImageStreamer.closeStream;
import static org.cocktailbot.drink.utils.UrlImageStreamer.openStream;

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
            String mentionAuthor = event.getAuthor().getAsMention();
            String message = event.getMessage().getContentRaw();
            RandomDrink randomDrink = randomDrinkService.getRandomDrinkFromAlcoholContentFlags(message);
            sendReturnMessage(event, mentionAuthor, randomDrink);
        }
    }

    private void sendReturnMessage(@NotNull MessageReceivedEvent event, String mentionAuthor, RandomDrink randomDrink) {
        if (!randomDrink.drinkName().name().isEmpty()) {
            sendSuccessMessage(event, mentionAuthor, randomDrink);
        } else {
            sendFailureMessage(event, mentionAuthor);
        }
    }

    private void sendSuccessMessage(MessageReceivedEvent event, String author, RandomDrink randomDrink) {
        String successMessage = String.format(
                "Hello %s!\nThis is your random drink: %s",
                author,
                randomDrink.drinkName().name());
        InputStream imageStream = openStream(randomDrink.drinkImageUrl().url());
        event.getChannel()
                .sendMessage(successMessage)
                .addFile(imageStream, "drink.png")
                .queue(success -> closeStream(imageStream), failure -> closeStream(imageStream));
    }

    private void sendFailureMessage(MessageReceivedEvent event, String author) {
        String failureMessage = String.format(
                "Hello %s!\nExceeded the maximum number of connection attempts, please try again later",
                author);
        event.getChannel()
                .sendMessage(failureMessage)
                .queue();
    }
}
