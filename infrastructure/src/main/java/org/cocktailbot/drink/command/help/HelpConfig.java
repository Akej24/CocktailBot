package org.cocktailbot.drink.command.help;

import org.cocktailbot.drink.command.validator.PrefixValidator;

public class HelpConfig {

    public static HelpCommand getInstance() {
        return new HelpCommand(PrefixValidator.getInstance());
    }
}
