package org.cocktailbot.drink.command.help;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.drink.command.validator.CommandValidator;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
class HelpCommand extends ListenerAdapter {

    private static final String COMMAND = "!help";
    private final CommandValidator commandValidator;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (commandValidator.validateCommand(event, COMMAND)) {
            String author = event.getAuthor().getAsMention();
            event.getChannel()
                    .sendMessage(buildReturnMessage(author))
                    .queue();
        }
    }

    private String buildReturnMessage(String author) {
        return String.format("""
        Hello %s, Here is a list of available commands:
            
        **!help** - _Shows a list of available commands_
        **!random** - _Draws a random drink_
        **!random -a** - _Draws a random alcoholic drink_
        **!random -na** - _Draws a random non-alcoholic drink_
        **!recipe <drink name>** - _Shows a recipe for the given drink_
        **!ingredient <ingredient name>** - _Shows information for the given drink_
        **!favourites** - _Shows a list of favourite drinks (max 50)_
        **!suggest <username> <drink name>** - _Suggest the user the given drink (max 50)_
        **!showsuggested** - _Shows a list of suggested drinks_
        **!accept <drink name>** - _Accepts the given drink and move it to 'to try' category_
        **!reject <drink name>** - _Rejects the given drink and remove it from suggested_
        **!totry** - _Shows a list of drinks to try (max 50)_
        
        To add drink to favourites click heart emote ❤ under recipe for the given drink and to remove drink from favourites just click cross emote ❌.
        
        This application is based on public https://www.thecocktaildb.com api, so when some commands do not return any result, it is possible that the api contains gaps that prevent the execution of individual functionalities on specific drinks (e.g. a drink may not have an image or a recipe)
        
        Thank you for using the bot!
        """, author);
    }
}
