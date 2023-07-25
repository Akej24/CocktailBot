package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.cocktailbot.core.*;

import javax.security.auth.login.LoginException;

public class CocktailBot {

    public static void main(String[] args) throws LoginException {
        JDA bot = JdaBot.buildBot();
        bot.addEventListener(RandomCocktailConfig.getInstance());
        bot.addEventListener(HowToMakeDrinkConfig.getInstance());
    }
}