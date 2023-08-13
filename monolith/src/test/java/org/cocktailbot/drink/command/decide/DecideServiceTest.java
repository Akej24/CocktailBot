package org.cocktailbot.drink.command.decide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class DecideServiceTest {

    private static final String testUsername = "test-username";
    private static final String testDrinkName = "test-drink-name";

    private DecideRepository testDecideRepository;
    private DecideService testDecideService;

    @BeforeEach
    void setUp() {
        testDecideRepository = mock(DecideRepository.class);
        testDecideService = new DecideService(testDecideRepository);
    }

    @Test
    @DisplayName("Should pass when successfully called repository get method one time and returned true")
    void acceptSuggestedDrink() {
        when(testDecideRepository.acceptSuggestedDrink(testUsername, testDrinkName))
                .thenReturn(true);
        var actual = testDecideService.acceptSuggestedDrink(testUsername, testDrinkName);
        verify(testDecideRepository, times(1)).acceptSuggestedDrink(testUsername, testDrinkName);
        assertTrue(actual);
    }

    @Test
    @DisplayName("Should pass when successfully called repository get method one time and returned false")
    void rejectSuggestedDrink() {
        when(testDecideRepository.rejectSuggestedDrink(testUsername, testDrinkName))
                .thenReturn(false);
        var actual = testDecideService.rejectSuggestedDrink(testUsername, testDrinkName);
        verify(testDecideRepository, times(1)).rejectSuggestedDrink(testUsername, testDrinkName);
        assertFalse(actual);
    }
}
