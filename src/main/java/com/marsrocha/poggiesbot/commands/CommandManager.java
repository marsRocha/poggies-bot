package com.marsrocha.poggiesbot.commands;

import com.marsrocha.poggiesbot.lavaplayer.AudioCommands;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private final CommonCommands commonCommands;
    private final AudioCommands audioCommands;

    public CommandManager() {
        commonCommands = new CommonCommands();
        audioCommands = new AudioCommands();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commonCommands.onSlashCommandInteraction(event);
        audioCommands.onSlashCommandInteraction(event);
    }

    // Guild commands -- instantly updated (max 100)
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        // get common commands
        commandData.addAll(commonCommands.getCommands());
        // get music commands
        commandData.addAll(audioCommands.getCommands());

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
