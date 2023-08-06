package org.cocktailbot.drink.command.decide;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

class DecideCommand extends ListenerAdapter {

    private static final String ACCEPT_COMMAND = "!accept";
    private static final String REJECT_COMMAND = "!reject";
    private final CommandValidator commandValidator;
    private final DecideService decideService;

    DecideCommand(CommandValidator commandValidator, DecideService decideService) {
        this.commandValidator = commandValidator;
        this.decideService = decideService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        boolean detectedAccept = commandValidator.validateCommand(event, ACCEPT_COMMAND);
        boolean detectedReject = commandValidator.validateCommand(event, REJECT_COMMAND);
        if (detectedAccept || detectedReject) {
            String username = event.getAuthor().getName().toLowerCase();
            String drinkName = event.getMessage().getContentRaw().substring(ACCEPT_COMMAND.length()).trim();
            String mentionAuthor = event.getAuthor().getAsMention();
            boolean status;
            String responseMessage;
            if (detectedAccept && !detectedReject) {
                status = decideService.acceptSuggestedDrink(username, drinkName);
                responseMessage = buildReturnAcceptMessage(mentionAuthor, drinkName, status);
            } else {
                status = decideService.rejectSuggestedDrink(username, drinkName);
                responseMessage = buildReturnRejectMessage(mentionAuthor, drinkName, status);
            }
            event.getChannel().sendMessage(responseMessage).queue();

        }
    }

    private String buildReturnAcceptMessage(String author, String drinkName, boolean status) {
        return String.format(
                "Hello %s!\n%s", author, status
                        ? "We could not accept your suggested drink: " + drinkName
                        : "Your suggested drink: " + drinkName + " has been accepted"
        );
    }

    private String buildReturnRejectMessage(String author, String drinkName, boolean status) {
        return String.format(
                "Hello %s!\n%s", author, status
                        ? "We could not reject your suggested drink: " + drinkName
                        : "Your suggested drink: " + drinkName + " has been rejected"
        );
    }
}
