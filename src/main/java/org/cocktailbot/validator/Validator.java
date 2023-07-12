package org.cocktailbot.validator;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Validator {

    boolean validateCommand(MessageReceivedEvent event, String correctCommandName, ValidationType validationType);

}
