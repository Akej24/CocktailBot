package org.cocktailbot;

import net.dv8tion.jda.api.JDA;

import javax.security.auth.login.LoginException;

public class CocktailBot {

    public static void main(String[] args) throws LoginException {
        JDA bot = JdaBot.buildBot();
        bot.upsertCommand("random", "Returns random drink").queue();
        bot.upsertCommand("recipe", "Returns recipe for given drink").queue();
        bot.upsertCommand("ingredient", "Returns ingredient info").queue();
        bot.upsertCommand("favourite", "Returns drinks marked as favourite").queue();
    }
}