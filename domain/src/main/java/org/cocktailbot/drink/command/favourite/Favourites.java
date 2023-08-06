package org.cocktailbot.drink.command.favourite;

import java.util.Set;

record Favourites(Set<Favourite> favourites) {

    static Favourites from(Set<Favourite> favourites) {
        return new Favourites(favourites);
    }
}
