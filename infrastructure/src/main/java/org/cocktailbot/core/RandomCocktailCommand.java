package org.cocktailbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

class RandomCocktailCommand extends ListenerAdapter {

    private static final String COMMAND = "!random";
    private final RandomDrinkService randomCocktailService;
    private final Validator validator;

    public RandomCocktailCommand(Validator validator, RandomDrinkService randomCocktailService) {
        this.validator = validator;
        this.randomCocktailService = randomCocktailService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String author = event.getAuthor().getName();
        if (validator.validateCommand(event, COMMAND)) {
            String drink = randomCocktailService.getCocktailNameJsonResponse();
            event.getChannel().sendMessage("Hello @" + author + "!\nThis is your random drink: " + drink)./*addFile(new File("")).*/queue();
        }
    }


}
