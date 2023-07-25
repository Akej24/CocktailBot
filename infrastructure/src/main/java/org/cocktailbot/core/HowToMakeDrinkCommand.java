package org.cocktailbot.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class HowToMakeDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!howtomake ";
    private final UrlResponseReader urlResponseReader;
    private final Validator validator;
    private final HowToMakeDrinkService howToMakeDrinkService;

    public HowToMakeDrinkCommand(Validator validator, UrlResponseReader urlResponseReader, HowToMakeDrinkService howToMakeDrinkService) {
        this.validator = validator;
        this.urlResponseReader = urlResponseReader;
        this.howToMakeDrinkService = howToMakeDrinkService;
    }

    @Override
    //CONTROLLER
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String author = event.getAuthor().getName();
        if (validator.validateCommand(event, COMMAND)) {
            //SERVICE
            String drinkNameFromMessage = message.substring(COMMAND.length());
            String drinkJsonResponse = urlResponseReader.getResponseFromUrl("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkNameFromMessage, "GET");
            String drinkInstructions = getInstructionsFromJson(drinkJsonResponse);
            event.getChannel().sendMessage("Hello " + author + "!\nThis is how to make " + drinkNameFromMessage + "\n\n" + drinkInstructions)./*addFile(new File("")).*/queue();
        }
    }

    private static String getInstructionsFromJson(String instructionsJsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(instructionsJsonResponse);
            JSONArray drinksArray = jsonObject.getJSONArray("drinks");
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString("strInstructions");
        } catch (JSONException e) {
            return "Drink does not exist";
        }
    }
}
