package org.cocktailbot.drink.reaction.validator;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.springframework.stereotype.Component;

@Component
public class CodepointValidator implements ReactionValidator {

    @Override
    public boolean validateReactionEvent(MessageReactionAddEvent event, String embedTitle) {
        return event.getUser() != null
                && !event.getUser().isBot()
                && !embedTitle.isEmpty();
    }

    @Override
    public boolean validateEmote(MessageReactionAddEvent event, String correctEmojiCodePoints) {
        return event.getReaction()
                .getEmoji()
                .getAsReactionCode()
                .equals(correctEmojiCodePoints);
    }
}
