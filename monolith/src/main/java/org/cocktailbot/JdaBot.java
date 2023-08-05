package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.cocktailbot.drink.command.favourite.FavouriteCommandConfig;
import org.cocktailbot.drink.command.suggest.SuggestConfig;
import org.cocktailbot.drink.reaction.favourite.FavouriteReactionConfig;
import org.cocktailbot.drink.command.ingredient.IngredientCommandConfig;
import org.cocktailbot.drink.command.random.RandomDrinkConfig;
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
                    .createDefault(TOKEN)
                    .setActivity(Activity.playing("Preparing drink"))
                    .addEventListeners(
                            RandomDrinkConfig.getInstance(),
                            RecipeCommandConfig.getInstance(),
                            IngredientCommandConfig.getInstance(),
                            FavouriteReactionConfig.getInstance(),
                            FavouriteCommandConfig.getInstance(),
                            SuggestConfig.getInstance()
                    )
                    .build();
        }
    }
}
