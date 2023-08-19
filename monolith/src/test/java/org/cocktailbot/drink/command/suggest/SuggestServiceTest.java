package org.cocktailbot.drink.command.suggest;

import org.cocktailbot.drink.drink_api.DrinkClient;
import org.cocktailbot.drink.drink_api.DrinkResponseReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SuggestServiceTest {

    private static final String testAuthor = "test-author";

    private SuggestRepository testSuggestRepository;
    private SuggestService testSuggestService;
    private DrinkClient testDrinkClient;
    private DrinkResponseReader testDrinkResponseReader;

    @BeforeEach
    void setUp() {
        testSuggestRepository = mock(SuggestRepository.class);
        testDrinkClient = mock(DrinkClient.class);
        testDrinkResponseReader = mock(DrinkResponseReader.class);
        testSuggestService = new SuggestService(testSuggestRepository, testDrinkClient, testDrinkResponseReader);
    }

    @Test
    @DisplayName("Should pass when returned true for correct params")
    void tryAddSuggestedDrink_withCorrectParams() {
        SuggestCommandParams testCorrectParams = new SuggestCommandParams("test-suggested-username", "test-drink-name");
        when(testDrinkClient.getDrink(testCorrectParams.drinkName()))
                .thenReturn("returned-drink-from-api");
        when(testDrinkResponseReader.getValueFromDrink("returned-drink-from-api", "strDrink"))
                .thenReturn("some-value");
        when(testSuggestRepository.saveDrinkToSuggestedUsername(testAuthor, testCorrectParams.drinkName(), testCorrectParams.suggestedUsername()))
                .thenReturn(true);
        assertTrue(testSuggestService.tryAddSuggestedDrink(testAuthor, testCorrectParams));
        verify(testSuggestRepository, times(1)).saveDrinkToSuggestedUsername(testAuthor, testCorrectParams.drinkName(), testCorrectParams.suggestedUsername());
    }

    @Test
    @DisplayName("Should pass when returned false for invalid params")
    void tryAddSuggestedDrink_withInvalidParams() {
        SuggestCommandParams testInvalidParams = new SuggestCommandParams("", "");
        when(testDrinkClient.getDrink(testInvalidParams.drinkName()))
                .thenReturn("returned-drink-from-api");
        when(testDrinkResponseReader.getValueFromDrink("returned-drink-from-api", "strDrink"))
                .thenReturn("some-value");
        when(testSuggestRepository.saveDrinkToSuggestedUsername(testAuthor, testInvalidParams.drinkName(), testInvalidParams.suggestedUsername()))
                .thenReturn(true);
        assertFalse(testSuggestService.tryAddSuggestedDrink(testAuthor, testInvalidParams));
        verify(testSuggestRepository, times(0)).saveDrinkToSuggestedUsername(testAuthor, testInvalidParams.drinkName(), testInvalidParams.suggestedUsername());
    }

    @Test
    @DisplayName("Should pass when returned false for correct suggest username and invalid drink name")
    void tryAddSuggestedDrink_withHalfCorrectParams() {
        SuggestCommandParams testHalfCorrectParams = new SuggestCommandParams("test-suggested-username", "");
        when(testDrinkClient.getDrink(testHalfCorrectParams.drinkName()))
                .thenReturn("returned-drink-from-api");
        when(testDrinkResponseReader.getValueFromDrink("returned-drink-from-api", "strDrink"))
                .thenReturn("some-value");
        when(testSuggestRepository.saveDrinkToSuggestedUsername(testAuthor, testHalfCorrectParams.drinkName(), testHalfCorrectParams.suggestedUsername()))
                .thenReturn(true);
        assertFalse(testSuggestService.tryAddSuggestedDrink(testAuthor, testHalfCorrectParams));
        verify(testSuggestRepository, times(0)).saveDrinkToSuggestedUsername(testAuthor, testHalfCorrectParams.drinkName(), testHalfCorrectParams.suggestedUsername());
    }

    @Test
    @DisplayName("Should pass when returned false for not existing drink")
    void tryAddSuggestedDrink_whenDrinkDoesNotExist_withCorrectParams() {
        SuggestCommandParams testCorrectParams = new SuggestCommandParams("test-suggested-username", "test-drink-name");
        when(testDrinkClient.getDrink(testCorrectParams.drinkName()))
                .thenReturn("does-not-exist");
        when(testDrinkResponseReader.getValueFromDrink("does-not-exist", "strDrink"))
                .thenReturn("");
        when(testSuggestRepository.saveDrinkToSuggestedUsername(testAuthor, testCorrectParams.drinkName(), testCorrectParams.suggestedUsername()))
                .thenReturn(true);
        assertFalse(testSuggestService.tryAddSuggestedDrink(testAuthor, testCorrectParams));
        verify(testSuggestRepository, times(0)).saveDrinkToSuggestedUsername(testAuthor, testCorrectParams.drinkName(), testCorrectParams.suggestedUsername());
    }
}
