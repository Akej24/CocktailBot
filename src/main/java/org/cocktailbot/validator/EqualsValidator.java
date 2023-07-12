package org.cocktailbot.validator;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public final class EqualsValidator implements Validator{

    private static EqualsValidator INSTANCE;

    private EqualsValidator() {
    }

    public static EqualsValidator getInstance() {
        return INSTANCE == null ? INSTANCE = new EqualsValidator() : INSTANCE;
    }

    @Override
    public boolean validateCommand(MessageReceivedEvent event, String correctCommandName, ValidationType validationType) {
        return event.getMessage().getContentRaw().equalsIgnoreCase(correctCommandName)
                && !event.getAuthor().isBot() && !event.getAuthor().isSystem();
    }
}
