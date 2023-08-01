package org.cocktailbot.drink.command.recipe;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

class DrinkRecipeCommand extends ListenerAdapter {

    private static final String COMMAND = "!recipe";
    private final Validator validator;
    private final DrinkRecipeService drinkRecipeService;

    public DrinkRecipeCommand(Validator validator, DrinkRecipeService drinkRecipeService) {
        this.validator = validator;
        this.drinkRecipeService = drinkRecipeService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String drinkMessageName = event.getMessage().getContentRaw().substring(COMMAND.length()+1);
            DrinkRecipe drinkRecipe = drinkRecipeService.getDrinkRecipe(drinkMessageName);
            String author = event.getAuthor().getAsMention();
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(drinkRecipe.drinkName().name())
                    .setImage(drinkRecipe.drinkImageUrl().url().toString())
                    .build();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, drinkRecipe))
                    .setEmbeds(embed)
                    .queue();
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
        if (drinkRecipe == null || drinkRecipe.recipeIngredients() == null) {
            return "No recipeIngredients found for this recipe";
        }
        return drinkRecipe.recipeIngredients().ingredients().entrySet().stream()
                .map(entry -> "- "
                        + entry.getKey().name()
                        + ": "
                        + entry.getValue().measure()
                        + "\n")
                .collect(Collectors.joining());
    }
}
