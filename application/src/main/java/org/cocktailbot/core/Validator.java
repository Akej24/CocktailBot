package org.cocktailbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Validator {

    boolean validateCommand(MessageReceivedEvent event, String correctCommandName);

}
