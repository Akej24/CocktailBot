package org.cocktailbot.drink.in_memory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;

import static org.mockito.Mockito.when;

public class InMemoryUser {

    public static void createForMessageReceivedEvent(MessageReceivedEvent testMessageReceivedEvent) {
        when(testMessageReceivedEvent.getAuthor())
                .thenReturn(createInMemoryUser());
    }

    public static void createForMessageReactionAddEvent(MessageReactionAddEvent testMessageReactionAddEvent) {
        when(testMessageReactionAddEvent.getUser())
                .thenReturn(createInMemoryUser());
    }

    @NotNull
    private static User createInMemoryUser() {
        return new User() {
            @NotNull
            @Override
            public String getName() {
                return "name";
            }

            @NotNull
            @Override
            public String getDiscriminator() {
                return "discriminator";
            }

            @Override
            public @NotNull String getAvatarId() {
                return "avatar-id";
            }

            @NotNull
            @Override
            public String getDefaultAvatarId() {
                return "default-avatar-id";
            }

            @NotNull
            @Override
            public RestAction<Profile> retrieveProfile() {
                throw new RuntimeException("Not implemented yet");
            }

            @NotNull
            @Override
            public String getAsTag() {
                return "tag";
            }

            @Override
            public boolean hasPrivateChannel() {
                return false;
            }

            @NotNull
            @Override
            public RestAction<PrivateChannel> openPrivateChannel() {
                throw new RuntimeException("Not implemented yet");
            }

            @NotNull
            @Override
            public List<Guild> getMutualGuilds() {
                throw new RuntimeException("Not implemented yet");
            }

            @Override
            public boolean isBot() {
                return false;
            }

            @Override
            public boolean isSystem() {
                return false;
            }

            @NotNull
            @Override
            public JDA getJDA() {
                throw new RuntimeException("Not implemented yet");
            }

            @NotNull
            @Override
            public EnumSet<UserFlag> getFlags() {
                throw new RuntimeException("Not implemented yet");
            }

            @Override
            public int getFlagsRaw() {
                throw new RuntimeException("Not implemented yet");
            }

            @NotNull
            @Override
            public String getAsMention() {
                return "as-mention";
            }

            @Override
            public long getIdLong() {
                return 1;
            }
        };
    }
}
