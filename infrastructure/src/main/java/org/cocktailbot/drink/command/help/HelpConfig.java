package org.cocktailbot.drink.command.help;

import lombok.AllArgsConstructor;
import org.cocktailbot.drink.command.validator.PrefixValidator;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class HelpConfig {

    private final PrefixValidator prefixValidator;

    public HelpCommand subscribeHelpCommand() {
        return new HelpCommand(prefixValidator);
    }
}
