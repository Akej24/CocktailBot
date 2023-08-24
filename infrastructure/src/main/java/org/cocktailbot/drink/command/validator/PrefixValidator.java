package org.cocktailbot.drink.command.validator;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class PrefixValidator implements CommandValidator {

    @Override
    public boolean validateCommand(MessageReceivedEvent event, String correctCommandName) {
        return event.getMessage().getContentRaw().startsWith(correctCommandName)
                && !event.getAuthor().isBot()
                && !event.getAuthor().isSystem();
    }
}
