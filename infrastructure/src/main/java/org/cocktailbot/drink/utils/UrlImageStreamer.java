package org.cocktailbot.drink.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlImageStreamer {

    public static InputStream openStream(URL imageUrl) {
        InputStream inputStream;
        try {
            inputStream = imageUrl.openStream();
        } catch (IOException e) {
            throw new RuntimeException("Error during opening image stream");
        }
        return inputStream;
    }

    public static void closeStream(InputStream imageStream) {
        try {
            imageStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Error during closing image stream");
        }
    }

}
