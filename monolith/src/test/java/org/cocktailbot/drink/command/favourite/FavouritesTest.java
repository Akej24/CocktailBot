package org.cocktailbot.drink.command.favourite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FavouritesTest {

    @Test
    @DisplayName("Should pass when the value from static fabric method is the same as value of constructor")
    void createFavouritesByConstructor_and_createFavouritesByStaticFabricMethod_and_checkResults() {
        var testFavourite = new Favourite("test-favourite");
        var testFavourites = Set.of(testFavourite);

        var actual = new Favourites(testFavourites);
        var expected = Favourites.from(testFavourites);

        assertEquals(expected.favourites().size(), actual.favourites().size());
        assertTrue(actual.favourites().contains(testFavourite));
        assertTrue(expected.favourites().contains(testFavourite));
    }

}