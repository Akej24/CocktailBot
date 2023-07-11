package org.cocktailbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Commands extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String author = event.getAuthor().getName();

        if (message.contains("drink") && !author.equals("Cocktail Bot")) {
            URL url;
            HttpURLConnection con;
            BufferedReader in;
            StringBuffer content = new StringBuffer();

            try {
                url = new URL("https://www.thecocktaildb.com/api/json/v1/1/random.php");
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                con.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println(content);

            JSONObject jsonObject = new JSONObject(content.toString());
            JSONArray drinksArray = jsonObject.getJSONArray("drinks");
            String strDrink = "";
            if (drinksArray.length() > 0) {
                JSONObject drinkObject = drinksArray.getJSONObject(0);
                strDrink = drinkObject.getString("strDrink");
            }

            event.getChannel().sendMessage("Hello " + author + "!\nThis is your random drink: " + strDrink).queue();
        }
    }
}
