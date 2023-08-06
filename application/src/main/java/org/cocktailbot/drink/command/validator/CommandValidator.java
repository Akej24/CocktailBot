package org.cocktailbot.drink.command.validator;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandValidator {

    boolean validateCommand(MessageReceivedEvent event, String correctCommandName);

}
