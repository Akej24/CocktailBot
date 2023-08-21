package org.cocktailbot.drink.drink_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DrinkJsonResponseReaderTest {

    private DrinkJsonResponseReader testDrinkJsonResponseReader;

    @BeforeEach
    void setUp() {
        testDrinkJsonResponseReader = DrinkJsonResponseReader.getInstance();
    }

    @Test
    @DisplayName("Should pass when returned expected values from drink json")
    void getValueFromDrink_withCorrectJson() {
        String testJson = "{\"drinks\": [{\"strDrink\": \"TestDrink\",\"strVideo\": null,\"strDrinkThumb\": \"https://test.jpg\"}]}";
        String expectedStrDrink = testDrinkJsonResponseReader.getValueFromDrink(testJson, "strDrink");
        String expectedStrVideo = testDrinkJsonResponseReader.getValueFromDrink(testJson, "strVideo");
        assertEquals("TestDrink", expectedStrDrink);
        assertEquals("", expectedStrVideo);
    }

    @Test
    @DisplayName("Should pass when returned empty string for invalid drink json")
    void getValueFromDrink_withIncorrectJson() {
        String testIncorrectMainNodeJson = "{\"incorrect-drinks-array-node\": [{\"strDrink\": \"TestDrink\",\"strVideo\": null}]}";
        String testIncorrectInternalNodeJson = "{\"drinks\": [{\"strIncorrectDrinkNode\": \"Margarita\",\"strVideo\": null}]}";
        String expectedValueForIncorrectMainNodeJson = testDrinkJsonResponseReader.getValueFromDrink(testIncorrectMainNodeJson, "strDrink");
        String expectedValueForIncorrectInternalNodeJson = testDrinkJsonResponseReader.getValueFromDrink(testIncorrectInternalNodeJson, "strDrink");
        assertEquals("", expectedValueForIncorrectMainNodeJson);
        assertEquals("", expectedValueForIncorrectInternalNodeJson);
    }

    @Test
    @DisplayName("Should pass when returned expected values from ingredient json")
    void getValueFromIngredient_withCorrectJson() {
        String testJson = "{\"ingredients\": [{\"strIngredient\": \"TestIngredient\",\"strType\": null}]}";
        String expectedStrIngredient = testDrinkJsonResponseReader.getValueFromIngredient(testJson, "strIngredient");
        String expectedStrType = testDrinkJsonResponseReader.getValueFromIngredient(testJson, "strType");
        assertEquals("TestIngredient", expectedStrIngredient);
        assertEquals("", expectedStrType);
    }

    @Test
    @DisplayName("Should pass when returned empty string for invalid ingredient json")
    void getValueIngredient_withIncorrectJson() {
        String testIncorrectMainNodeJson = "{\"incorrect-ingredients-array-node\": [{\"strIngredient\": \"TestIngredient\",\"strType\": \"null\"}]}";
        String testIncorrectInternalNodeJson = "{\"ingredients\": [{\"strIncorrectIngredientNode\": \"TestIngredient\",\"strType\": \"null\"}]}";
        String expectedValueForIncorrectMainNodeJson = testDrinkJsonResponseReader.getValueFromIngredient(testIncorrectMainNodeJson, "strDrink");
        String expectedValueForIncorrectInternalNodeJson = testDrinkJsonResponseReader.getValueFromIngredient(testIncorrectInternalNodeJson, "strDrink");
        assertEquals("", expectedValueForIncorrectMainNodeJson);
        assertEquals("", expectedValueForIncorrectInternalNodeJson);
    }
}