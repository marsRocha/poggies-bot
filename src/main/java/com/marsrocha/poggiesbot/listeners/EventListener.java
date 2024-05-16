package com.marsrocha.poggiesbot.listeners;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.toLowerCase().contains("ping")) {
            event.getChannel().sendMessage("pling pling").queue();
            event.getChannel().sendMessage("https://tenor.com/view/peepo-gif-20356408").queue();
        } else if (message.toLowerCase().contains("morning squad") || message.toLowerCase().contains("good morning")) {
            morningLine(event);
        } else if (message.toLowerCase().contains("poggies") || message.contains(event.getJDA().getSelfUser().getAsMention())) {
            if (message.toLowerCase().contains("hello") || message.toLowerCase().contains("hi")) {
                helloLine(event);
            } else if (message.toLowerCase().contains("shut up") || message.toLowerCase().contains("stfu")) {
                shutupLine(event);
            }else if (message.toLowerCase().contains("who") && message.toLowerCase().contains("you")) {
                event.getChannel().sendMessage("im your new god").queue();
            }
            else {
                randomLine(event);
            }
        }
    }

    public void morningLine(MessageReceivedEvent event) {
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

    public void helloLine(MessageReceivedEvent event) {
        Random random = new Random();
        int index = random.nextInt(3);

        switch (index) {
            case 0:
                event.getChannel().sendMessage("harroo").queue();
                break;
            case 1:
                event.getChannel().sendMessage("https://tenor.com/view/binoculars-binoculous-peepo-detective-peep-gif-9523079724857954693").queue();
                event.getChannel().sendMessage("hello there").queue();
                break;
            case 2:
                event.getChannel().sendMessage("https://tenor.com/view/smile-happy-umm-frog-peepo-gif-26307842").queue();
                event.getChannel().sendMessage("do i know u?").queue();
                break;
        }
    }

    public void shutupLine(MessageReceivedEvent event) {
        Random random = new Random();
        int index = random.nextInt(4);

        switch (index) {
            case 0 -> {
                event.getChannel().sendMessage("https://tenor.com/view/angry-evil-pepe-the-frog-red-gif-26333702").queue();
                event.getChannel().sendMessage("what did you just say to my son, punk?").queue();
            }
            case 1 -> {
                event.getChannel().sendMessage("https://tenor.com/view/peepo-leave-gif-26415215").queue();
            }
            case 2 -> {
                event.getChannel().sendMessage("no u skank").queue();
            }
            case 3 -> {
                event.getChannel().sendMessage(event.getAuthor().getAsMention()).queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention()).queue();
                event.getChannel().sendMessage(event.getAuthor().getAsMention()).queue();
                event.getChannel().sendMessage("u shut up").queue();
            }
        }
    }

    public void randomLine(MessageReceivedEvent event) {
        Random random = new Random();
        int index = random.nextInt(6);

        switch (index) {
            case 0 -> {
                event.getChannel().sendMessage("yes.... ??").queue();
            }
            case 1 -> {
                event.getChannel().sendMessage("did u say my name?").queue();
            }
            case 2 -> {
                event.getChannel().sendMessage("hm?").queue();
            }
            case 3 -> {
                event.getChannel().sendMessage("im busy").queue();
            }
            case 4 -> {
                event.getChannel().sendMessage("beep boop").queue();
            }
            case 5 -> {
                event.getChannel().sendMessage("👀").queue();
            }
        }
    }


    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        TextChannel generalChannel = event.getGuild().getSystemChannel();
        generalChannel.sendMessage("harroo").queue();
        generalChannel.sendMessage("https://tenor.com/view/pepenet-pepecoin-pepe-peepo-pepenet-wave-gif-16025988913151322329").queue();
    }
}
