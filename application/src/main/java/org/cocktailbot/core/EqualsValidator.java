package org.cocktailbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

final class EqualsValidator implements Validator {

    private static EqualsValidator INSTANCE;

    private EqualsValidator() {
    }

    public static EqualsValidator getInstance() {
        return INSTANCE == null ? INSTANCE = new EqualsValidator() : INSTANCE;
    }

    @Override
    public boolean validateCommand(MessageReceivedEvent event, String correctCommandName) {
        return event.getMessage().getContentRaw().equalsIgnoreCase(correctCommandName)
                && !event.getAuthor().isBot() && !event.getAuthor().isSystem();
    }
}
