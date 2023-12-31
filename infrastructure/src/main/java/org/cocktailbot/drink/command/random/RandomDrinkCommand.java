package org.cocktailbot.drink.command.random;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import static org.cocktailbot.drink.utils.UrlImageStreamer.closeStream;
import static org.cocktailbot.drink.utils.UrlImageStreamer.openStream;

@AllArgsConstructor
class RandomDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!random";
    private final CommandValidator commandValidator;
    private final RandomDrinkService randomDrinkService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
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
                .addFiles(FileUpload.fromData(imageStream, "drink.png"))
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
