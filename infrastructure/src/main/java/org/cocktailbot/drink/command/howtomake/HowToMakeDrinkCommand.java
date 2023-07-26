package org.cocktailbot.drink.command.howtomake;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

class HowToMakeDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!howtomake ";
    private final Validator validator;
    private final HowToMakeDrinkService howToMakeDrinkService;

    public HowToMakeDrinkCommand(Validator validator, HowToMakeDrinkService howToMakeDrinkService) {
        this.validator = validator;
        this.howToMakeDrinkService = howToMakeDrinkService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String drinkName = event.getMessage().getContentRaw().substring(COMMAND.length());
            String drinkRecipe = howToMakeDrinkService.getDrinkRecipe(drinkName);
            String author = event.getAuthor().getName();
            String message = String.format(
                            "Hello @%s!\n%s",
                            author,
                            (drinkRecipe.isEmpty() ? "Your drink does not exist" : "This is how to make " + drinkName + "\n" + drinkRecipe)
            );
            event.getChannel().sendMessage(message)./*addFile(new File("")).*/queue();
        }
    }

}
