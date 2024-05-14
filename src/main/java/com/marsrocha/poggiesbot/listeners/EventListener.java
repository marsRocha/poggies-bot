package com.marsrocha.poggiesbot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if(message.contains("ping")) {
            event.getChannel().sendMessage("pling pling").queue();
            event.getChannel().sendMessage("https://tenor.com/view/peepo-gif-20356408").queue();
        }
        else if(message.toLowerCase().contains("morning squad") || message.toLowerCase().contains("good morning")) {
            String[] messages = {
                    "monin' squaddies!",
                    "monin' UwU",
                    "wakey wakey",
                    "wakey froggy squaddies!"
            };

            Random random = new Random();
            int index = random.nextInt(messages.length);

            event.getChannel().sendMessage(messages[index]).queue();
        }
    }
}
