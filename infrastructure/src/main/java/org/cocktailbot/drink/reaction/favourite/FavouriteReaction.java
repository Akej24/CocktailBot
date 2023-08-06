package org.cocktailbot.drink.reaction.favourite;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.shared.Emojis;
import org.cocktailbot.drink.reaction.validator.CodepointValidator;
import org.jetbrains.annotations.NotNull;

class FavouriteReaction extends ListenerAdapter {

    private final CodepointValidator codepointValidator;
    private final FavouriteService favouriteService;

    FavouriteReaction(CodepointValidator codepointValidator, FavouriteService favouriteService) {
        this.codepointValidator = codepointValidator;
        this.favouriteService = favouriteService;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        String embedTitle = getEmbedTitle(event);
        if(codepointValidator.validateReactionEvent(event, embedTitle)) {
            String username = event.getChannel().getName();
            if(codepointValidator.validateEmote(event, Emojis.HEART)){
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
