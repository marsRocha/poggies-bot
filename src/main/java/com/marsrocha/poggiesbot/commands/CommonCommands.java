package com.marsrocha.poggiesbot.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommonCommands {

    // Guild commands -- instantly updated (max 100)
    public List<CommandData> getCommands() {
        List<CommandData> commandData = new ArrayList<>();

        // Command: /say <message> [channel]
        OptionData option1 = new OptionData(
                OptionType.STRING,
                "message",
                "The message you want to the bot to say")
                .setRequired(true);
        OptionData option2 = new OptionData(
                OptionType.CHANNEL,
                "channel",
                "The channel you want to send the message")
                .setRequired(false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        commandData.add(Commands.slash("say", "Make Poggies say something!").addOptions(option1, option2));

        // Command: /action <action> [member]
        OptionData option3 = new OptionData(
                OptionType.STRING,
                "action",
                "The type of the action")
                .setRequired(true)
                .addChoice("Hug", "hug")
                .addChoice("Dance", "dance");
        OptionData option4 = new OptionData(
                OptionType.USER,
                "user",
                "The user to send to")
                .setRequired(false);
        commandData.add(Commands.slash("action", "Make Poggies perform an action!").addOptions(option3, option4));

        OptionData option5 = new OptionData(
                OptionType.USER,
                "user",
                "The user to send to")
                .setRequired(true);
        commandData.add(Commands.slash("annoy", "Make Poggies annoy someone!").addOptions(option5));

        return commandData;
    }

    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();

        switch (command) {
            case "say" -> { sayCommand(event); }
            case "action" -> { actionCommand(event); }
            case "annoy" -> { annoyCommand(event); }
        }
    }

    public void sayCommand(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping messageOption = event.getOption("message");
        String message = Objects.requireNonNull(messageOption).getAsString();

        MessageChannel channel;
        OptionMapping channelOption = event.getOption("channel");
        if (channelOption != null) {
            channel = channelOption.getAsChannel().asTextChannel();
        } else {
            channel = event.getChannel();
        }

        channel.sendMessage(message).queue();
        event.reply("Your message was sent!").setEphemeral(true).queue();
    }

    public void actionCommand(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping option = event.getOption("action");
        String emotion = Objects.requireNonNull(option).getAsString();

        String replyMessage = "";
        switch (emotion.toLowerCase()) {
            case "hug" -> {
                replyMessage = "lemme hug u";
                // Send the GIF
                event.getChannel().sendMessage("https://tenor.com/view/hug-pepe-pepe-hug-hugging-gif-22352687").queue();
            }
            case "dance" -> {
                replyMessage = "lets bogey!";
                event.getChannel().sendMessage("https://tenor.com/view/peepo-gif-20356408").queue();
            }
        }

        OptionMapping user = event.getOption("user");
        if (user != null) {
            replyMessage += " " + user.getAsUser().getAsMention();
        }

        event.reply(replyMessage).queue();
    }

    public void annoyCommand(@NotNull SlashCommandInteractionEvent event) {
        OptionMapping user = event.getOption("user");

        event.reply("hehehe done").setEphemeral(true).queue();
        event.getChannel().sendMessage(user.getAsUser().getAsMention()).queue();
        event.getChannel().sendMessage(user.getAsUser().getAsMention()).queue();
        event.getChannel().sendMessage(user.getAsUser().getAsMention()).queue();
        event.getChannel().sendMessage("https://tenor.com/view/peepo-gif-20356408").queue();
    }
}
