package org.cocktailbot.drink.command.suggest;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
            SuggestCommandParams suggestCommandParams = getParamsFromMessage(event.getMessage().getContentRaw());
            String suggestedUsername = suggestCommandParams.suggestedUsername();
            String drinkName = suggestCommandParams.drinkName();
            if (suggestedUsername.isEmpty() || drinkName.isEmpty()) return;

            List<Member> availableUsers = new ArrayList<>();
            event.getGuild().loadMembers().onSuccess(availableUsers::addAll);
            if(!containsSuggestedUsername(availableUsers, suggestedUsername)) return;

            boolean success = suggestService.tryAddSuggestedDrink(event.getAuthor().getName(), drinkName, suggestedUsername);
            String author = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, suggestedUsername, drinkName, success))
                    .queue();
        }
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

    private boolean containsSuggestedUsername(List<Member> availableUsers, String suggestedUsername) {
        for (Member member : availableUsers) {
            if (member.getEffectiveName().equalsIgnoreCase(suggestedUsername)) {
                return true;
            }
        }
        return false;
    }

    private String buildReturnMessage(String author, String suggestedUsername, String drinkName, boolean success) {
        return String.format(
                "Hello %s!\n%s", author, !success
                        ? "Your suggested drink does not exist"
                        : "You have just suggested " + suggestedUsername
                        + " a drink: " + drinkName
        );
    }

}
