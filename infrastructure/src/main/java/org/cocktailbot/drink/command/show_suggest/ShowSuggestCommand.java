package org.cocktailbot.drink.command.show_suggest;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

@AllArgsConstructor
class ShowSuggestCommand extends ListenerAdapter {

    private static final String COMMAND = "!showsuggested";
    private final CommandValidator commandValidator;
    private final ShowSuggestService showSuggestService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String username = event.getAuthor().getName().toLowerCase();
            SuggestedDrinks suggestedDrinks = showSuggestService.getSuggestedDrinksForUsername(username);
            String author = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author, suggestedDrinks))
                    .queue();
        }
    }

    private String buildReturnMessage(String author, SuggestedDrinks suggestedDrinks) {
        return String.format(
                "Hello %s!\n%s", author, suggestedDrinks == null || suggestedDrinks.drinks().isEmpty()
                        ? "Your do not have any suggested drinks"
                        : "There are your suggested drinks:\n"
                        + suggestedDrinks.drinks().entrySet().stream()
                        .map(entry -> "- " + entry.getKey().name() + " ~ " + entry.getValue().nick() + "\n")
                        .collect(Collectors.joining())
        );
    }
}
