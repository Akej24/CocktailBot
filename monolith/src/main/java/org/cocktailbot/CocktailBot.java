package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import org.cocktailbot.drink.command.favourite.FavouriteDrinkConfig;
import org.cocktailbot.drink.command.ingredient.IngredientConfig;
import org.cocktailbot.drink.command.recipe.DrinkRecipeConfig;
import org.cocktailbot.drink.command.random.RandomDrinkConfig;

import javax.security.auth.login.LoginException;

public class CocktailBot {

    public static void main(String[] args) throws LoginException {
        JDA bot = JdaBot.buildBot();
        bot.addEventListener(
                RandomDrinkConfig.getInstance(),
                DrinkRecipeConfig.getInstance(),
                IngredientConfig.getInstance(),
                FavouriteDrinkConfig.getInstance()
        );
    }
}