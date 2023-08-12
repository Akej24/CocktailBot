package org.cocktailbot.drink.reaction.favourite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class FavouriteServiceTest {

    private static final String testUsername = "test-username";
    private static final String testEmbedTitle = "test-embed-title";

    private FavouriteRepository testFavouriteRepository;
    private FavouriteService testFavouriteService;

    @BeforeEach
    void setUp() {
        testFavouriteRepository = mock(FavouriteRepository.class);
        testFavouriteService = new FavouriteService(testFavouriteRepository);
    }

    @Test
    @DisplayName("Should pass when successfully called repository add method one time")
    void saveDrinkToFavourites() {
        testFavouriteService.saveDrinkToFavourites(testUsername, testEmbedTitle);
        verify(testFavouriteRepository, times(1)).addUserFavouriteDrink(testUsername, testEmbedTitle);
    }

    @Test
    @DisplayName("Should pass when successfully called repository remove method one time")
    void removedDrinkFromFavourites() {
        testFavouriteService.removedDrinkFromFavourites(testUsername, testEmbedTitle);
        verify(testFavouriteRepository, times(1)).removeUserFavouriteDrink(testUsername, testEmbedTitle);
    }
}