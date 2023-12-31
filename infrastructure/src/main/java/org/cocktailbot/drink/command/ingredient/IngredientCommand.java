package org.cocktailbot.drink.command.ingredient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Slf4j
@AllArgsConstructor
class IngredientCommand extends ListenerAdapter {

    private static final String COMMAND = "!ingredient";
    private final CommandValidator commandValidator;
    private final IngredientService ingredientService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String ingredientMessageName = getIngredientMessageName(event);
            Ingredient ingredient = ingredientService.getIngredient(ingredientMessageName);
            String mentionAuthor = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(mentionAuthor, ingredient))
                    .queue();
        }
    }

    private static String getIngredientMessageName(MessageReceivedEvent event) {
        try {
            return event.getMessage().getContentRaw().substring(COMMAND.length()+1);
        } catch (IndexOutOfBoundsException e) {
            log.info("Invalid !ingredient command");
            return "";
        }
    }

    private String buildReturnMessage(String mentionAuthor, Ingredient ingredient) {
        return String.format(
                "Hello %s!\n%s", mentionAuthor, ingredient.ingredientName().name().equals("")
                        ? "Your ingredient does not exist"
                        : String.format("""
                        
                        Ingredient random fact:
                        - %s
                        
                        Type:
                        - %s
                        
                        Alcohol content:
                        - %s
                        """,
                        drawRandomFact(ingredient.ingredientFacts()),
                        ingredient.ingredientType().type(),
                        ingredient.alcoholContent().getName())
        );
    }

    private String drawRandomFact(IngredientFacts ingredientFacts) {
        int randomIndex = new Random().nextInt(ingredientFacts.facts().size());
        return ingredientFacts.facts().get(randomIndex);
    }

}
