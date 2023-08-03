package org.cocktailbot.drink.drink_api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class DrinkJsonClient implements DrinkClient {

    private static DrinkJsonClient INSTANCE;
    private static final String DRINK_URL = "https://www.thecocktaildb.com/api/json/v1/";
    private static final String API_KEY = "1/";

    private DrinkJsonClient() {
    }

    public static DrinkJsonClient getInstance() {
        return INSTANCE == null ? INSTANCE = new DrinkJsonClient() : INSTANCE;
    }

    @Override
    public String getRandomDrink() {
        return getResponseFromEndpoint( "random.php", "GET");
    }

    @Override
    public String getDrink(String drinkName) {
        return getResponseFromEndpoint("search.php?s=" + drinkName, "GET");
    }

    @Override
    public String getIngredient(String ingredientName) {
        return getResponseFromEndpoint("search.php?i=" + ingredientName, "GET");
    }

    private String getResponseFromEndpoint(String endpoint, String method) {
        try {
            URL url = new URL(DRINK_URL + API_KEY + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try (AutoCloseable ignored = connection::disconnect) {
                connection.setRequestMethod(method);
                return readResponse(connection);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid endpoint: " + endpoint);
        } catch (Exception e) {
            throw new RuntimeException("Error during opening or closing connection");
        }
    }

    private static String readResponse(HttpURLConnection connection) {
        try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Could not read json response from url");
        }
    }
}
