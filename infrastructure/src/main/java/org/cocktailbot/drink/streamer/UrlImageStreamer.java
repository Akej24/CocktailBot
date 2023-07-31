package org.cocktailbot.drink.streamer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlImageStreamer {

    public static InputStream openStream(URL imageUrl) {
        InputStream inputStream;
        try {
            inputStream = imageUrl.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during opening image stream");
        }
        return inputStream;
    }

    public static void closeStream(InputStream imageStream) {
        try {
            imageStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error during closing image stream");
        }
    }

}
