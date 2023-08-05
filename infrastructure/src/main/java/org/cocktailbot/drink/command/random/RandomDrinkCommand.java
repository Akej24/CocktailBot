package org.cocktailbot.drink.command.random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.utils.UrlImageStreamer;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

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
            RandomDrink randomDrink = randomDrinkService.getRandomDrinkWithAlcoholContent(message);
            if (!randomDrink.drinkName().name().isEmpty()) {
                sendSuccessMessage(event, author, randomDrink);
            } else {
                sendFailureMessage(event, author);
            }
        }
    }

    private void sendSuccessMessage(MessageReceivedEvent event, String author, RandomDrink randomDrink) {
        InputStream imageStream = UrlImageStreamer.openStream(randomDrink.drinkImageUrl().url());
        event.getChannel()
                .sendMessage(buildSuccessMessage(author, randomDrink))
                .addFile(imageStream, "drink.png")
                .queue(success -> UrlImageStreamer.closeStream(imageStream), failure -> UrlImageStreamer.closeStream(imageStream));
    }

    private static String buildSuccessMessage(String author, RandomDrink randomDrink) {
        return String.format("Hello %s!\nThis is your random drink: %s",
                author,
                randomDrink.drinkName().name()
        );
    }

    private void sendFailureMessage(MessageReceivedEvent event, String author) {
        event.getChannel().sendMessage(buildFailureMessage(author)).queue();
    }

    private static String buildFailureMessage(String author) {
        return String.format("Hello %s!\n"
                + "Exceeded the maximum number of connection attempts, please try again later", author
        );
    }
}
