package org.cocktailbot.drink.command.random;

import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class RandomDrinkServiceTest {

    private DrinkClient testDrinkClient;
    private DrinkResponseReader testDrinkResponseReader;
    private RandomDrinkService testRandomDrinkService;

    @BeforeEach
    void setUp() {
        testDrinkClient = mock(DrinkClient.class);
        testDrinkResponseReader = mock(DrinkResponseReader.class);
        testRandomDrinkService = new RandomDrinkService(testDrinkClient, testDrinkResponseReader);
    }

    @Test
    @DisplayName("Should pass when successfully found just random drink for message with no flags")
    void getRandomDrink_fromMessageWithNoFlags() {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("Non alcoholic");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("Bloody Mary");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("https://image.jpg");
        String messageWithNoFlags = "!random";

        var randomDrink = testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithNoFlags);
        assertEquals("Bloody Mary", randomDrink.drinkName().name());
        assertEquals("https://image.jpg", randomDrink.drinkImageUrl().url().toString());

    }

    @Test
    @DisplayName("Should pass when successfully found alcoholic drink for alcoholic flag message")
    void getRandomDrink_fromMessageWithAlcoholicFlag() {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("Alcoholic");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("Bloody Mary");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("https://image.jpg");
        String messageWithAlcoholicFlag = "!random -a";

        var randomDrink = testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithAlcoholicFlag);
        assertEquals("Bloody Mary", randomDrink.drinkName().name());
        assertEquals("https://image.jpg", randomDrink.drinkImageUrl().url().toString());
    }

    @Test
    @DisplayName("Should pass when successfully found non alcoholic drink for non alcoholic flag message")
    void getRandomDrink_fromMessageWithNonAlcoholicFlag() {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("Non alcoholic");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("Bloody Mary");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("https://image.jpg");
        String messageWithNonAlcoholicFlag = "!random -na";

        var randomDrink = testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithNonAlcoholicFlag);
        assertEquals("Bloody Mary", randomDrink.drinkName().name());
        assertEquals("https://image.jpg", randomDrink.drinkImageUrl().url().toString());
    }

    @Test
    @DisplayName("Should pass when returned not empty drink for message with both flags and chose random from them")
    void getRandomDrink_fromMessageWithBothFlags() {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("Alcoholic");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("Bloody Mary");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("https://image.jpg");
        String messageWithBothFlags = "!random -na -a";

        var randomDrink = testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithBothFlags);
        assertFalse(randomDrink.drinkName().name().isEmpty());
        assertNotNull(randomDrink.drinkImageUrl().url());
    }

    @Test
    @DisplayName("Should pass when returned just random drink for message with not existing flag")
    void getRandomDrink_fromMessageWithFakeFlag() {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("Other alcoholic type");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("Bloody Mary");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("https://image.jpg");
        String messageWithFakeFlag = "!random -x";

        var randomDrink = testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithFakeFlag);
        assertEquals("Bloody Mary", randomDrink.drinkName().name());
        assertEquals("https://image.jpg", randomDrink.drinkImageUrl().url().toString());
    }

    @Test
    @DisplayName("Should pass when returned empty drink and invoked get random drink (maximum attempts + 1) times")
    void getRandomDrink_whenAboveMaximumAttempts() throws NoSuchFieldException, IllegalAccessException {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("");
        String messageWithFlag = "!random -a";

        int shortenDelay = 1;
        Field delayField = RandomDrinkService.class.getDeclaredField("delay");
        delayField.setAccessible(true);
        delayField.setInt(testRandomDrinkService, shortenDelay);

        var randomDrink = testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithFlag);
        verify(testDrinkClient, times(101)).getRandomDrink();
        assertTrue(randomDrink.drinkName().name().isEmpty());
        assertNull(randomDrink.drinkImageUrl().url());

    }

    @Test
    @DisplayName("Should pass when threw runtime exception with malformed message for malformed drink image url")
    void getRandomDrink_withMalformedDrinkImageUrl() {
        when(testDrinkClient.getRandomDrink()).thenReturn("random-drink");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strAlcoholic"))
                .thenReturn("Alcoholic");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrink"))
                .thenReturn("Bloody Mary");
        when(testDrinkResponseReader.getValueFromDrink("random-drink", "strDrinkThumb"))
                .thenReturn("malformed-drink-image-url");
        String messageWithFlag = "!random -a";
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> testRandomDrinkService.getRandomDrinkFromAlcoholContentFlags(messageWithFlag)
        );
        assertTrue(exception.getMessage().contains("is malformed"));
    }
}
