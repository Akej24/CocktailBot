package org.cocktailbot.drink.command.recipe;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.utils.Emojis;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

class RecipeCommand extends ListenerAdapter {

    private static final String COMMAND = "!recipe";
    private final Validator validator;
    private final RecipeService recipeService;

    RecipeCommand(Validator validator, RecipeService recipeService) {
        this.validator = validator;
        this.recipeService = recipeService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String drinkMessageName = event.getMessage().getContentRaw().substring(COMMAND.length()).trim();
            if (drinkMessageName.isEmpty()) return;
            Recipe recipe = recipeService.getDrinkRecipe(drinkMessageName);
            String author = event.getAuthor().getAsMention();
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(recipe.drinkName().name())
                    .setImage(recipe.drinkImageUrl().url().toString())
                    .build();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, recipe))
                    .setEmbeds(embed)
                    .queue(message -> {
                        message.addReaction(Emojis.HEART).queue();
                        message.addReaction(Emojis.CROSS).queue();
                    });
        }
    }

    private String buildReturnMessage(String author, Recipe recipe) {
        return String.format(
                "Hello %s!\n%s", author, recipe.drinkName().name().equals("")
                        ? "Your drink does not exist"
                        : "This is how to make " + recipe.drinkName().name()
                        + "\n\nIngredients:\n" + generateDrinkIngredients(recipe)
                        + "\nInstruction:\n- " + recipe.instruction().instruction().replaceAll("\\. ", ".\n- ")
        );
    }

    private String generateDrinkIngredients(Recipe recipe) {
        if (recipe == null || recipe.recipeIngredients() == null) {
            return "No recipeIngredients found for this recipe";
        }
        return recipe.recipeIngredients().ingredients().entrySet().stream()
                .map(entry -> "- "
                        + entry.getKey().name()
                        + ": "
                        + entry.getValue().measure()
                        + "\n")
                .collect(Collectors.joining());
    }
}
