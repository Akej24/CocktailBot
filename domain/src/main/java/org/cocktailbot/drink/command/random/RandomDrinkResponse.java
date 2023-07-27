package org.cocktailbot.drink.command.random;

import java.net.URL;

record RandomDrinkResponse (
        String drinkName,
        URL drinkImageUrl,
        boolean responseStatus
) {}
