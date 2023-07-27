package org.cocktailbot.drink.command.random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
            RandomDrinkResponse response = randomDrinkService.getRandomDrinkWithAlcoholContent(message);
            try (InputStream imageStream = response.drinkImageUrl().openStream()) {
                event.getChannel()
                        .sendMessage(buildReturnMessage(author, response.drinkName(), response.responseStatus()))
                        .addFile(imageStream, "drink.png")
                        .queue(success -> closeStream(imageStream), failure -> closeStream(imageStream));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeStream(InputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildReturnMessage(String author, String drinkName, boolean responseStatus) {
        return String.format("Hello %s!\n%s", author, responseStatus
                ? "This is your random drink: " + drinkName
                : "Exceeded the maximum number of connection attempts or something else went wrong, please try again"
        );
    }
}
