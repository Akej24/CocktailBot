package org.cocktailbot.drink.command.favourite;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.validator.Validator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

class FavouriteDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!favourite";
    private final FavouriteDrinkService favouriteDrinkService;
    private final Validator validator;

    public FavouriteDrinkCommand(Validator validator, FavouriteDrinkService favouriteDrinkService) {
        this.validator = validator;
        this.favouriteDrinkService = favouriteDrinkService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String username = event.getAuthor().getName();
            Favourites favourites = favouriteDrinkService.getUserFavouritesDrink(username);
            event.getChannel()
                    .sendMessage(buildReturnMessage(username, favourites))
                    .queue();
        }
    }

    private String buildReturnMessage(String username, Favourites favourites) {
        return String.format(
                "Hello %s!\n%s", username, favourites.favourites().isEmpty()
                        ? "You do not have any favourite drinks"
                        : "These are your favourite drinks:\n"
                        + favourites.favourites().stream()
                            .map(favourite -> "- " + favourite.name() + " ❤\n")
                            .collect(Collectors.joining())
        );
    }
}
