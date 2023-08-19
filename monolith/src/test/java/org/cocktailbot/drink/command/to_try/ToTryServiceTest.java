package org.cocktailbot.drink.command.to_try;

import org.cocktailbot.drink.command.shared.value_object.DrinkName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ToTryServiceTest {

    private static final String testUsername = "test-username";
    private static final DrinkName testDrinkName = new DrinkName("test-drink-name");

    private ToTryRepository testToTryRepository;
    private ToTryService testToTryService;

    @BeforeEach
    void setUp() {
        testToTryRepository = mock(ToTryRepository.class);
        testToTryService = new ToTryService(testToTryRepository);
    }

    @Test
    @DisplayName("Should pass when successfully called repository method one time and returned drinks contain one element")
    void removeTriedDrink() {
        when(testToTryRepository.getUserToTryDrinks(testUsername))
                .thenReturn(ToTryDrinks.from(Set.of(testDrinkName)).drinks());
        assertEquals(1, testToTryService.getUserToTryDrinks(testUsername).drinks().size());
        verify(testToTryRepository, times(1)).getUserToTryDrinks(testUsername);
    }
}
