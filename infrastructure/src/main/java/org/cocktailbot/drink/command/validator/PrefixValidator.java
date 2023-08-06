package org.cocktailbot.drink.command.validator;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PrefixValidator implements CommandValidator {

    private static PrefixValidator INSTANCE;

    private PrefixValidator() {
    }

    public static PrefixValidator getInstance() {
        return INSTANCE == null ? INSTANCE = new PrefixValidator() : INSTANCE;
    }

    @Override
    public boolean validateCommand(MessageReceivedEvent event, String correctCommandName) {
        return event.getMessage().getContentRaw().startsWith(correctCommandName)
                && !event.getAuthor().isBot()
                && !event.getAuthor().isSystem();
    }
}
