package org.cocktailbot.drink.command.favourite;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

class FavouriteCommand extends ListenerAdapter {

    private static final String COMMAND = "!favourites";
    private final FavouriteService favouriteService;
    private final Validator validator;

    public FavouriteCommand(Validator validator, FavouriteService favouriteService) {
        this.validator = validator;
        this.favouriteService = favouriteService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
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
