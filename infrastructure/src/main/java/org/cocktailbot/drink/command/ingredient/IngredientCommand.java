package org.cocktailbot.drink.command.ingredient;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.ingredient.value_object.IngredientFacts;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

class IngredientCommand extends ListenerAdapter {

    private static final String COMMAND = "/ingredient";
    private final IngredientService ingredientService;
    private final Validator validator;

    public IngredientCommand(Validator validator, IngredientService ingredientService) {
        this.validator = validator;
        this.ingredientService = ingredientService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String ingredientMessageName = event.getMessage().getContentRaw().substring(COMMAND.length()+1);
            Ingredient ingredient = ingredientService.getIngredient(ingredientMessageName);
            String author = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, ingredient))
                    .queue();
        }
    }

    private String buildReturnMessage(String author, Ingredient ingredient) {
        return String.format(
                "Hello %s!\n%s", author, ingredient.ingredientName().name().equals("")
                        ? "Your ingredient does not exist"
                        : "\nIngredient random fact:\n- " + drawRandomFact(ingredient.ingredientFacts())
                        + "\n\nType:\n- " + ingredient.ingredientType().type()
                        + "\n\nAlcohol content:\n- " + ingredient.ingredientAlcoholContent().getName()
        );
    }

    private String drawRandomFact(IngredientFacts ingredientFacts) {
        int randomIndex = new Random().nextInt(ingredientFacts.facts().size());
        return ingredientFacts.facts().get(randomIndex);
    }

}
