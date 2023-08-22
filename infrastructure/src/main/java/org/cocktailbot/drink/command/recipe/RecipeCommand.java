package org.cocktailbot.drink.command.recipe;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.utils.Emojis;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

class RecipeCommand extends ListenerAdapter {

    private static final String COMMAND = "!recipe";
    private final CommandValidator commandValidator;
    private final RecipeService recipeService;

    RecipeCommand(CommandValidator commandValidator, RecipeService recipeService) {
        this.commandValidator = commandValidator;
        this.recipeService = recipeService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String drinkMessageName = getDrinkMessageName(event);
            Recipe recipe = recipeService.getDrinkRecipe(drinkMessageName);
            String author = event.getAuthor().getAsMention();
            sendReturnMessage(event, author, recipe);
        }
    }

    private String getDrinkMessageName(@NotNull MessageReceivedEvent event) {
        try {
            return event.getMessage().getContentRaw().substring(COMMAND.length() + 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid !recipe command");
            return "";
        }
    }

    private void sendReturnMessage(MessageReceivedEvent event, String author, Recipe recipe) {
        if(recipe.drinkName().name().isBlank()){
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, recipe))
                    .queue();
        } else {
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle(recipe.drinkName().name())
                    .setImage(recipe.drinkImageUrl().url().toString())
                    .build();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, recipe))
                    .setEmbeds(embed)
                    .queue(message -> {
                        message.addReaction(Emoji.fromUnicode(Emojis.HEART)).queue();
                        message.addReaction(Emoji.fromUnicode(Emojis.CROSS)).queue();
                    });
        }
    }

    private String buildReturnMessage(String author, Recipe recipe) {
        return String.format(
                "Hello %s!\n%s", author, recipe.drinkName().name().isBlank()
                        ? "Your drink does not exist"
                        : String.format("""
                        This is how to make %s
                        
                        Ingredients:
                        %s
                        Instruction:
                        - %s
                        """,
                        recipe.drinkName().name(),
                        generateDrinkIngredients(recipe),
                        recipe.instruction().instruction().replaceAll("\\. ", ".\n- "))
        );
    }

    private String generateDrinkIngredients(Recipe recipe) {
        if (recipe.recipeIngredients() == null || recipe.recipeIngredients().ingredients().isEmpty()) {
            return "No ingredients found for this recipe";
        }
        return recipe.recipeIngredients()
                .ingredients()
                .entrySet()
                .stream()
                .map(entry -> String.format("- %s: %s\n",
                        entry.getKey().name(),
                        entry.getValue().measure()))
                .collect(Collectors.joining());
    }
}
