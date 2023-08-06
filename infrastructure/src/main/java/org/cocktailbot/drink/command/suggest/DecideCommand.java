package org.cocktailbot.drink.command.suggest;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

class DecideCommand extends ListenerAdapter {

    private static final String ACCEPT_COMMAND = "!accept";
    private static final String REJECT_COMMAND = "!reject";
    private final Validator validator;
    private final DecideService decideService;

    DecideCommand(Validator validator, DecideService decideService) {
        this.validator = validator;
        this.decideService = decideService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, ACCEPT_COMMAND)) {
            String username = event.getAuthor().getName().toLowerCase();
            String drinkName = event.getMessage().getContentRaw().substring(ACCEPT_COMMAND.length()).trim();
            boolean status = decideService.acceptSuggestedDrink(username, drinkName);
            String author = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, drinkName, status))
                    .queue();
        } else if (validator.validateCommand(event, REJECT_COMMAND)) {
            String username = event.getAuthor().getName().toLowerCase();
            String drinkName = event.getMessage().getContentRaw().substring(ACCEPT_COMMAND.length()).trim();
            boolean status = decideService.rejectSuggestedDrink(username, drinkName);
            String author = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, drinkName, status))
                    .queue();
        }
    }

    private String buildReturnMessage(String author, String drinkName, boolean status) {
        return String.format(
                "Hello %s!\n%s", author, status
                        ? "We could not accept your suggested drink: " + drinkName
                        : "Your suggested drink: " + drinkName + " has been accepted"
        );
    }
}
