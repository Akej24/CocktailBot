package org.cocktailbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

interface Validator {

    boolean validateCommand(MessageReceivedEvent event, String correctCommandName);

}
