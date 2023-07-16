package org.cocktailbot.application;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class RandomCocktailCommand extends ListenerAdapter {

    private static final String COMMAND = "!random";
    private final UrlResponseReader urlResponseReader;
    private final Validator validator;

    public RandomCocktailCommand(Validator validator, UrlResponseReader urlResponseReader) {
        this.validator = validator;
        this.urlResponseReader = urlResponseReader;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String author = event.getAuthor().getName();
        if (validator.validateCommand(event, COMMAND)) {
            String randomCocktailJson = urlResponseReader.getResponseFromUrl("https://www.thecocktaildb.com/api/json/v1/1/random.php", "GET");
            String drink = getCocktailNameFromJson(randomCocktailJson);
            event.getChannel().sendMessage("Hello @" + author + "!\nThis is your random drink: " + drink)./*addFile(new File("")).*/queue();
        }
    }

    private static String getCocktailNameFromJson(String randomCocktailJsonResponse) {
        JSONObject jsonObject = new JSONObject(randomCocktailJsonResponse);
        JSONArray drinksArray = jsonObject.getJSONArray("drinks");
        if (drinksArray.length() > 0) {
            JSONObject drinkObject = drinksArray.getJSONObject(0);
            return drinkObject.getString("strDrink");
        }
        return "not found";
    }
}
