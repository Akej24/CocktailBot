package org.cocktailbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class Commands extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String author = event.getAuthor().getName();
        if (message.equalsIgnoreCase("!drink") && !author.equals("Cocktail Bot")) {
            String randomCocktailJson = UrlResponseReader.getJsonResponseFromUrl("https://www.thecocktaildb.com/api/json/v1/1/random.php", "GET");
            String drink = getCocktailNameFromJson(randomCocktailJson);
            event.getChannel().sendMessage("Hello " + author + "!\nThis is your random drink: " + drink)./*addFile(new File("")).*/queue();
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
