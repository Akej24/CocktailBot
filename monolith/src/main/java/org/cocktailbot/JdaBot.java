package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.cocktailbot.drink.reaction.favourite.FavouriteDrinkConfig;
import org.cocktailbot.drink.command.ingredient.IngredientConfig;
import org.cocktailbot.drink.command.random.RandomDrinkConfig;
import org.cocktailbot.drink.command.recipe.DrinkRecipeConfig;

import javax.security.auth.login.LoginException;

class JdaBot {

    private static final String TOKEN = "MTEyODM2OTI5MzIyMzAxMDQ0NQ.GIyxiX.xBoehSHfa1MnbuXXUfc86kgoVjusBeD0JsTi4E";
    private static JDA bot;

    private JdaBot() {
    }

    static JDA buildBot() throws LoginException {
        return bot == null ?
                bot = JDABuilder
                        .createDefault(TOKEN)
                        .setActivity(Activity.playing("Preparing drink"))
                        .addEventListeners(
                                RandomDrinkConfig.getInstance(),
                                DrinkRecipeConfig.getInstance(),
                                IngredientConfig.getInstance(),
                                FavouriteDrinkConfig.getInstance()
                        )
                        .build()
                : bot;
    }
}
