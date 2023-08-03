package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.cocktailbot.drink.command.favourite.FavouriteCommandConfig;
import org.cocktailbot.drink.reaction.favourite.FavouriteReactionConfig;
import org.cocktailbot.drink.command.ingredient.IngredientCommandConfig;
import org.cocktailbot.drink.command.random.RandomConfig;
import org.cocktailbot.drink.command.recipe.RecipeCommandConfig;

import javax.security.auth.login.LoginException;

class JdaBot {

    private static final String TOKEN = "MTEyODM2OTI5MzIyMzAxMDQ0NQ.GIyxiX.xBoehSHfa1MnbuXXUfc86kgoVjusBeD0JsTi4E";
    private static JDA bot;

    private JdaBot() {
    }

    static void buildBot() throws LoginException {
        if (bot == null) {
            bot = JDABuilder
                    .createLight(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                    .setActivity(Activity.playing("Preparing drink"))
                    .addEventListeners(
                            RandomConfig.getInstance(),
                            RecipeCommandConfig.getInstance(),
                            IngredientCommandConfig.getInstance(),
                            FavouriteReactionConfig.getInstance(),
                            FavouriteCommandConfig.getInstance()
                    )
                    .build();
        }
    }
}
