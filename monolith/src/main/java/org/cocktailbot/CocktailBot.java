package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import org.cocktailbot.drink.command.how_to_make.HowToMakeDrinkConfig;
import org.cocktailbot.drink.command.random.RandomDrinkConfig;

import javax.security.auth.login.LoginException;

public class CocktailBot {

    public static void main(String[] args) throws LoginException {
        JDA bot = JdaBot.buildBot();
        bot.addEventListener(RandomDrinkConfig.getInstance());
        bot.addEventListener(HowToMakeDrinkConfig.getInstance());
    }
}