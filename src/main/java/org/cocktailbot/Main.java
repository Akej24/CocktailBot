package org.cocktailbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {

    private static final String TOKEN = "MTEyODM2OTI5MzIyMzAxMDQ0NQ.Gj_YKF.kB3IpuRGwwKIj5G01df9Z09-5ypJOlFOBsyE_8";

    public static void main(String[] args) throws LoginException {
        JDA bot = JDABuilder
                .createDefault(TOKEN)
                .setActivity(Activity.playing("Preparing drink"))
                .build();

        bot.addEventListener(new Commands());
    }
}