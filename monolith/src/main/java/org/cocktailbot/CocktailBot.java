package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import org.cocktailbot.drink.command.howtomake.HowToMakeDrinkConfig;
import org.cocktailbot.drink.command.random.RandomCocktailConfig;

import javax.security.auth.login.LoginException;

public class CocktailBot {

    public static void main(String[] args) throws LoginException {
        JDA bot = JdaBot.buildBot();
        bot.addEventListener(RandomCocktailConfig.getInstance());
        bot.addEventListener(HowToMakeDrinkConfig.getInstance());
    }
}