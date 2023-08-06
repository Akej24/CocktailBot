package org.cocktailbot.drink.reaction.validator;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public interface ReactionValidator {

    boolean validateReactionEvent(MessageReactionAddEvent event, String embedTitle);

    boolean validateEmote(MessageReactionAddEvent event, String correctEmojiCodePoints);

}
