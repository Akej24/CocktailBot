package org.cocktailbot.drink.command.favourite;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

@AllArgsConstructor
class FavouriteCommand extends ListenerAdapter {

    private static final String COMMAND = "!favourites";
    private final CommandValidator commandValidator;
    private final FavouriteService favouriteService;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String mentionUsername = event.getAuthor().getAsMention();
            String author = event.getAuthor().getName();
            Favourites favourites = favouriteService.getUserFavouritesDrink(author);
            event.getChannel()
                    .sendMessage(buildReturnMessage(mentionUsername, favourites))
                    .queue();
        }
    }

    private String buildReturnMessage(String author, Favourites favourites) {
        return String.format(
                "Hello %s!\n%s", author, favourites.favourites().isEmpty()
                        ? "You do not have any favourite drinks"
                        : "These are your favourite drinks:\n"
                        + favourites.favourites().stream()
                            .map(favourite -> String.format("- %s ❤\n", favourite.name()))
                            .collect(Collectors.joining())
        );
    }
}
