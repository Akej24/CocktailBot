package org.cocktailbot.drink.command.suggest;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class SuggestCommand extends ListenerAdapter {

    private static final String COMMAND = "!suggest";
    private final CommandValidator commandValidator;
    private final SuggestService suggestService;

    SuggestCommand(CommandValidator commandValidator, SuggestService suggestService) {
        this.commandValidator = commandValidator;
        this.suggestService = suggestService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String author = event.getAuthor().getName();
            SuggestCommandParams params = getParamsFromMessage(event.getMessage().getContentRaw());
            boolean success = checkUserExists(params.suggestedUsername(), event.getGuild().getMembers());
            success = suggestService.tryAddSuggestedDrink(author, params);
            String mentionAuthor = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(mentionAuthor, params.suggestedUsername(), params.drinkName(), success))
                    .queue();
        }
    }

    private boolean checkUserExists(String suggestedUsername, List<Member> availableUsers) {
        return availableUsers.stream().anyMatch(member -> member
                .getEffectiveName().equalsIgnoreCase(suggestedUsername));
    }

    private SuggestCommandParams getParamsFromMessage(String message) {
        try {
            int firstSpace = message.indexOf(" ");
            int secondSpace = message.indexOf(" ", firstSpace + 1);
            String suggestedUsername = message
                    .substring(COMMAND.length(), secondSpace)
                    .trim();
            String drinkName = message
                    .substring(secondSpace + 1)
                    .trim();
            return new SuggestCommandParams(suggestedUsername, drinkName);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid !suggest command");
            return new SuggestCommandParams("", "");
        }
    }

    private String buildReturnMessage(String author, String suggestedUsername, String drinkName, boolean success) {
        return String.format(
                "Hello %s!\n%s", author, !success
                        ? "Your suggested drink or user does not exist (invalid params) or user has a maximum number of suggested drinks"
                        : "You have just suggested " + suggestedUsername
                        + " a drink: " + drinkName
        );
    }

}
