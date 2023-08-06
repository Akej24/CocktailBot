package org.cocktailbot.drink.reaction.validator;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class CodepointValidator implements ReactionValidator {

    private static CodepointValidator INSTANCE;

    private CodepointValidator() {
    }

    public static CodepointValidator getInstance() {
        return INSTANCE == null ? INSTANCE = new CodepointValidator() : INSTANCE;
    }

    @Override
    public boolean validateReactionEvent(MessageReactionAddEvent event, String embedTitle) {
        return event.getUser() != null
                && !event.getUser().isBot()
                && !embedTitle.isEmpty();
    }

    @Override
    public boolean validateEmote(MessageReactionAddEvent event, String correctEmojiCodePoints) {
        return event.getReaction()
                .getReactionEmote()
                .getAsCodepoints()
                .equals(correctEmojiCodePoints);
    }
}
