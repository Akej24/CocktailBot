package org.cocktailbot.drink.reaction.favourite;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.utils.Emojis;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class FavouriteDrinkReaction extends ListenerAdapter {

    private final FavouriteDrinkService favouriteDrinkService;

    FavouriteDrinkReaction(FavouriteDrinkService favouriteDrinkService) {
        this.favouriteDrinkService = favouriteDrinkService;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        String embedTitle = getEmbedTitle(event);
        if(validateEmoteEvent(event, embedTitle)) {
            String username = getUsername(event);
            if(validateEmote(event, Emojis.HEART)){
                favouriteDrinkService.saveDrinkToFavourites(username, embedTitle);
            } else if(validateEmote(event, Emojis.CROSS)) {
                favouriteDrinkService.removedDrinkFromFavourites(username, embedTitle);
            }
        }
    }

    private String getUsername(MessageReactionAddEvent event) {
        try {
            return Objects.requireNonNull(event.getUser()).getName();
        } catch (NullPointerException e) {
            return "";
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

    private boolean validateEmoteEvent(MessageReactionAddEvent event, String embedTitle) {
        return event.getUser() != null
                && !event.getUser().isBot()
                && !embedTitle.isEmpty();
    }

    private boolean validateEmote(MessageReactionAddEvent event, String correctEmojiCodePoints) {
        return event.getReaction()
                .getReactionEmote()
                .getAsCodepoints()
                .equals(correctEmojiCodePoints);
    }
}
