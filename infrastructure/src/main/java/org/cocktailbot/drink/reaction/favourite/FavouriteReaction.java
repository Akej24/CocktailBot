package org.cocktailbot.drink.reaction.favourite;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.utils.Emojis;
import org.cocktailbot.drink.reaction.validator.CodepointValidator;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@AllArgsConstructor
class FavouriteReaction extends ListenerAdapter {

    private final CodepointValidator codepointValidator;
    private final FavouriteService favouriteService;

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        String embedTitle = getEmbedTitle(event);
        if(codepointValidator.validateReactionEvent(event, embedTitle)) {
            String username = Objects.requireNonNull(event.getUser()).getName();
            if(codepointValidator.validateEmote(event, Emojis.HEART)) {
                favouriteService.saveDrinkToFavourites(username, embedTitle);
            } else if(codepointValidator.validateEmote(event, Emojis.CROSS)) {
                favouriteService.removedDrinkFromFavourites(username, embedTitle);
            }
        }
    }

    private String getEmbedTitle(MessageReactionAddEvent event) {
        try {
            Message message = event.getChannel().retrieveMessageById(event.getMessageId()).complete();
            return message.getEmbeds().get(0).getTitle();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
