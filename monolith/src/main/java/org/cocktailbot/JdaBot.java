package org.cocktailbot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.cocktailbot.drink.command.decide.DecideConfig;
import org.cocktailbot.drink.command.favourite.FavouriteCommandConfig;
import org.cocktailbot.drink.command.help.HelpConfig;
import org.cocktailbot.drink.command.ingredient.IngredientCommandConfig;
import org.cocktailbot.drink.command.random.RandomDrinkConfig;
import org.cocktailbot.drink.command.recipe.RecipeCommandConfig;
import org.cocktailbot.drink.command.show_suggest.ShowSuggestConfig;
import org.cocktailbot.drink.command.suggest.SuggestConfig;
import org.cocktailbot.drink.command.to_try.ToTryConfig;
import org.cocktailbot.drink.command.tried.TriedConfig;
import org.cocktailbot.drink.reaction.favourite.FavouriteReactionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
@AllArgsConstructor
class JdaBot {

    private static final String TOKEN = readFromTokenFiles();

    private final RandomDrinkConfig randomDrinkConfig;
    private final RecipeCommandConfig recipeCommandConfig;
    private final IngredientCommandConfig ingredientCommandConfig;
    private final FavouriteReactionConfig favouriteReactionConfig;
    private final FavouriteCommandConfig favouriteCommandConfig;
    private final SuggestConfig suggestConfig;
    private final ShowSuggestConfig showSuggestConfig;
    private final DecideConfig decideConfig;
    private final ToTryConfig toTryConfig;
    private final HelpConfig helpConfig;
    private final TriedConfig triedConfig;

    @Bean
    void buildBot() {
        try {
            JDABuilder.createDefault(TOKEN)
                    .setActivity(Activity.playing("Preparing drink"))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .addEventListeners(
                            randomDrinkConfig.subscribeRandomDrinkCommand(),
                            recipeCommandConfig.subscribeRecipeCommand(),
                            ingredientCommandConfig.subscribeIngredientCommand(),
                            favouriteReactionConfig.subscribeFavouriteReaction(),
                            favouriteCommandConfig.subscribeFavouriteCommand(),
                            suggestConfig.subscribeSuggestCommand(),
                            showSuggestConfig.subscribeShowSuggestedCommand(),
                            decideConfig.subscribeDecideCommand(),
                            toTryConfig.subscribeToTryCommand(),
                            helpConfig.subscribeHelpCommand(),
                            triedConfig.subscribeTriedCommand()
                    )
                    .build();
        } catch(InvalidTokenException e ) {
            log.info("Invalid token in token.txt file, could not start application");
        }
    }

    static String readFromTokenFiles() {
        String[] filePaths = {"token.txt", "src/test/resources/token.txt"};
        for (String filePath : filePaths) {
            try {
                return new String(Files.readAllBytes(Paths.get(filePath))).trim();
            } catch (IOException e) {
                log.info("Could not read from {}, trying to read from a different location", filePath);
            }
        }
        log.info("Could not read token from token.txt file");
        throw new RuntimeException("Could not read token from token.txt file");
    }
}
