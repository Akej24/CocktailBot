package org.cocktailbot.drink.command.tried;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TriedServiceTest {

    private static final String testUsername = "test-username";
    private static final String testDrinkName = "test-drink-name";

    private TriedRepository testTriedRepository;
    private TriedService testTriedService;

    @BeforeEach
    void setUp() {
        testTriedRepository = mock(TriedRepository.class);
        testTriedService = new TriedService(testTriedRepository);
    }

    @Test
    @DisplayName("Should pass when successfully called repository method one time")
    void removeTriedDrink() {
        when(testTriedRepository.removeTriedDrink(testUsername, testDrinkName)).thenReturn(true);
        boolean status = testTriedService.removeTriedDrink(testUsername, testDrinkName);
        verify(testTriedRepository, times(1)).removeTriedDrink(testUsername, testDrinkName);
        assertTrue(status);
    }
}