package org.cocktailbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class HowToMakeDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!howtomake ";
    private final Validator validator;
    private final HowToMakeDrinkService howToMakeDrinkService;

    public HowToMakeDrinkCommand(Validator validator, HowToMakeDrinkService howToMakeDrinkService) {
        this.validator = validator;
        this.howToMakeDrinkService = howToMakeDrinkService;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (validator.validateCommand(event, COMMAND)) {
            String drinkNameFromMessage = event.getMessage().getContentRaw().substring(COMMAND.length());
            String drinkInstructions = howToMakeDrinkService.getDrinkJsonResponse(drinkNameFromMessage);
            String author = event.getAuthor().getName();
            event.getChannel().sendMessage("Hello " + author + "!\nThis is how to make " + drinkNameFromMessage + "\n\n" + drinkInstructions)./*addFile(new File("")).*/queue();
        }
    }

}
