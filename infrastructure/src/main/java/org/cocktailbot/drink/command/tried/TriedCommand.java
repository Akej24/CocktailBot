package org.cocktailbot.drink.command.tried;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

class TriedCommand extends ListenerAdapter {

    private static final String COMMAND = "!tried";
    private final CommandValidator commandValidator;
    private final TriedService triedService;

    public TriedCommand(CommandValidator commandValidator, TriedService triedService) {
        this.commandValidator = commandValidator;
        this.triedService = triedService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String username = event.getAuthor().getName().toLowerCase();
            String drinkName = event.getMessage().getContentRaw().substring(COMMAND.length()).trim();
            boolean status = triedService.removeTriedDrink(username, drinkName);
            String mentionAuthor = event.getAuthor().getAsMention();
            event.getChannel().sendMessage(buildReturnMessage(mentionAuthor, drinkName, status)).queue();

        }
    }

    private String buildReturnMessage(String author, String drinkName, boolean status) {
        return String.format(
                "Hello %s!\n%s", author, !status
                        ? "We could not remove your tried drink: " + drinkName
                        : "Your tried drink: " + drinkName + " has been removed"
        );
    }
}
