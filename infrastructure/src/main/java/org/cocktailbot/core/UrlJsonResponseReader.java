package org.cocktailbot.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class UrlJsonResponseReader implements UrlResponseReader {

    private static UrlJsonResponseReader INSTANCE;

    private UrlJsonResponseReader() {
    }

    public static UrlJsonResponseReader getInstance() {
        return INSTANCE == null ? INSTANCE = new UrlJsonResponseReader() : INSTANCE;
    }

    @Override
    public String getResponseFromUrl(String urlString, String method) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try (AutoCloseable ignored = connection::disconnect) {
                connection.setRequestMethod(method);
                return readJsonResponse(connection);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + urlString);
        } catch (Exception e) {
            throw new RuntimeException("Error during connection or closing");
        }
    }

    private static String readJsonResponse(HttpURLConnection connection) {
        try (var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Could not read json response from url");
        }
    }
}
