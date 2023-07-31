package org.cocktailbot.drink.command.how_to_make;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.streamer.UrlImageStreamer;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.stream.Collectors;

class HowToMakeDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!howtomake";
    private final Validator validator;
    private final HowToMakeDrinkService howToMakeDrinkService;

    public HowToMakeDrinkCommand(Validator validator, HowToMakeDrinkService howToMakeDrinkService) {
        this.validator = validator;
        this.howToMakeDrinkService = howToMakeDrinkService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String drinkMessageName = event.getMessage().getContentRaw().substring(COMMAND.length()+1);
            DrinkRecipe drinkRecipe = howToMakeDrinkService.getDrinkRecipe(drinkMessageName);
            String author = event.getAuthor().getAsMention();
            InputStream imageStream = UrlImageStreamer.openStream(drinkRecipe.drinkImageUrl().url());
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, drinkRecipe))
                    .addFile(imageStream, "drink.png")
                    .queue(success -> UrlImageStreamer.closeStream(imageStream), failure -> UrlImageStreamer.closeStream(imageStream));
        }
    }

    private String buildReturnMessage(String author, DrinkRecipe drinkRecipe) {
        return String.format(
                "Hello %s!\n%s", author, drinkRecipe.drinkName().name().equals("")
                        ? "Your drink does not exist"
                        : "This is how to make " + drinkRecipe.drinkName().name()
                        + "\n\nIngredients:\n" + generateDrinkIngredients(drinkRecipe)
                        + "\nInstruction:\n- " + drinkRecipe.instruction().instruction().replaceAll("\\. ", ".\n- ")
        );
    }

    private String generateDrinkIngredients(DrinkRecipe drinkRecipe) {
        if (drinkRecipe == null || drinkRecipe.ingredients() == null) {
            return "No ingredients found for this recipe";
        }
        return drinkRecipe.ingredients().ingredients().entrySet().stream()
                .map(entry -> "- "
                        + entry.getKey().ingredient()
                        + ": "
                        + entry.getValue().measure()
                        + "\n")
                .collect(Collectors.joining());
    }
}
