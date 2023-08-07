package org.cocktailbot.drink.command.to_try;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

class ToTryCommand extends ListenerAdapter {

    private static final String COMMAND = "!totry";
    private final CommandValidator commandValidator;
    private final ToTryService toTryService;

    ToTryCommand(CommandValidator commandValidator, ToTryService toTryService) {
        this.commandValidator = commandValidator;
        this.toTryService = toTryService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String username = event.getAuthor().getName().toLowerCase();
            ToTryDrinks toTryDrinks = toTryService.getUserToTryDrinks(username);
            String mentionAuthor = event.getAuthor().getAsMention();
            event.getChannel().sendMessage(buildReturnMessage(mentionAuthor, toTryDrinks)).queue();

        }
    }

    private String buildReturnMessage(String author, ToTryDrinks toTryDrinks) {
        return String.format(
                "Hello %s!\n%s", author, toTryDrinks.drinks() == null || toTryDrinks.drinks().isEmpty()
                        ? "You do not have any to try drinks"
                        : "There are your drinks to try:\n" + generateToTryDrinks(toTryDrinks)
        );
    }

    private String generateToTryDrinks(ToTryDrinks toTryDrinks) {
        return toTryDrinks.drinks()
                .stream()
                .map(drink -> String.format("- %s\n", drink.name()))
                .collect(Collectors.joining());
    }
}
