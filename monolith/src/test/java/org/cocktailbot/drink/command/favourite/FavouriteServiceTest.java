package org.cocktailbot.drink.command.favourite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FavouriteServiceTest {

    private static final String testUsername = "test-username";

    private FavouriteRepository testFavouriteRepository;
    private FavouriteService testFavouriteService;

    @BeforeEach
    void setUp() {
        testFavouriteRepository = mock(FavouriteRepository.class);
        testFavouriteService = new FavouriteService(testFavouriteRepository);
    }

    @Test
    @DisplayName("Should pass when successfully called repository get method one time adn returned correct favourite drinks with one element")
    void getUserFavouritesDrink() {
        when(testFavouriteRepository.getUserFavouriteDrinks(testUsername))
                .thenReturn(Set.of(new Favourite("test-favourite")));
        var actual = testFavouriteService.getUserFavouritesDrink(testUsername);
        verify(testFavouriteRepository, times(1)).getUserFavouriteDrinks(testUsername);
        var expected = Favourites.from(testFavouriteRepository.getUserFavouriteDrinks(testUsername));
        assertEquals(expected.favourites().size(), actual.favourites().size());
    }
}
