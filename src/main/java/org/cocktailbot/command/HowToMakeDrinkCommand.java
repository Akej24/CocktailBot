package org.cocktailbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.cocktailbot.reader.UrlResponseReader;
import org.cocktailbot.validator.ValidationType;
import org.cocktailbot.validator.Validator;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HowToMakeDrinkCommand extends ListenerAdapter {

    private static final String COMMAND = "!howtomake ";
    private final Validator validator;

    public HowToMakeDrinkCommand(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String author = event.getAuthor().getName();
        if (validator.validateCommand(event, COMMAND, ValidationType.PREFIX)) {
            String drinkNameFromMessage = message.substring(COMMAND.length());
            String drinkJsonResponse = UrlResponseReader.getJsonResponseFromUrl("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkNameFromMessage, "GET");
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
