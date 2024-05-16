package com.marsrocha.poggiesbot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AudioCommands {

    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
    private final AudioPlayer player;
    private final TrackScheduler trackScheduler;

    public AudioCommands() {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);

        player = audioPlayerManager.createPlayer();
        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);
    }

    // Guild commands -- instantly updated (max 100)
    public List<CommandData> getCommands() {
        List<CommandData> commandData = new ArrayList<>();

        // Command: /play <music>
        OptionData option1 = new OptionData(
                OptionType.STRING,
                "music",
                "Name of the song to play")
                .setRequired(true);
        commandData.add(Commands.slash("play", "Poggies plays/queues a song!").addOptions(option1));

        // Command: /stop
        commandData.add(Commands.slash("stop", "Poggies stops playing music."));

        // Command: /pause
        commandData.add(Commands.slash("pause", "Poggies pauses music."));

        // Command: /repeat
        commandData.add(Commands.slash("repeat", "Poggies puts music on repeat."));

        return commandData;
    }

    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();

        switch (command) {
            case "play" -> { playCommand(event); }
            case "stop" -> { stopCommand(event); }
            case "pause" -> { pauseCommand(event); }
            case "repeat" -> { repeatCommand(event); }
        }
    }

    public void playCommand(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;

        String music = event.getOption("music").getAsString();

        // Check if user is inside a voice channel
        if (event.getMember().getVoiceState().getChannel() == null) {
            event.reply("u need to bee in a voice channel! dumdum???").queue();
            event.getChannel().sendMessage("https://tenor.com/view/nopers-peepo-gif-20205003").queue();
            return;
        }
        // Get user's voice channel
        VoiceChannel channel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

        // Check if bot is already in a voice channel and if it's the same as the user
        if (event.getGuild().getSelfMember().getVoiceState().getChannel() != null) {
            if(event.getGuild().getSelfMember().getVoiceState().getChannel() != event.getMember().getVoiceState().getChannel()) {
                event.reply("u need to bee in the same voice channel as me!").queue();
                return;
            }
        }
        else {
            // enter the voice channel
            event.getGuild().getAudioManager().openAudioConnection(channel);
            event.getChannel().sendMessage("lets partiiiiiiiii").queue();
            event.getChannel().sendMessage("https://tenor.com/view/happy-gif-19334421").queue();
        }

        PlayTrack(event, music);
    }

    public void stopCommand(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;

        // Check if user is inside a voice channel
        if (event.getMember().getVoiceState().getChannel() == null) {
            event.reply("u need to bee in a voice channel! dumdum???").queue();
            event.getChannel().sendMessage("https://tenor.com/view/nopers-peepo-gif-20205003").queue();
            return;
        }
        // Get user's voice channel
        VoiceChannel channel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

        // Check if bot is already in a voice channel and if it's the same as the user
        if (event.getGuild().getSelfMember().getVoiceState().getChannel() != null) {
            if(event.getGuild().getSelfMember().getVoiceState().getChannel() != channel) {
                event.reply("u need to bee in the same voice channel as me!").queue();
            }
            else {
                // disconnect from the voice channel
                event.getGuild().getAudioManager().closeAudioConnection();
                event.reply("bye then").queue();
                event.getChannel().sendMessage("https://tenor.com/view/peepo-leave-gif-26415215").queue();
            }
        }
        else {
            event.reply("im not even playing music, leb me alone").queue();
        }
    }

    public void pauseCommand(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;

        // Check if user is inside a voice channel
        if (event.getMember().getVoiceState().getChannel() == null) {
            event.reply("u need to bee in a voice channel! dumdum???").queue();
            event.getChannel().sendMessage("https://tenor.com/view/nopers-peepo-gif-20205003").queue();
            return;
        }
        // Get user's voice channel
        VoiceChannel channel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

        // Check if bot is already in a voice channel and if it's the same as the user
        if (event.getGuild().getSelfMember().getVoiceState().getChannel() != null) {
            if(event.getGuild().getSelfMember().getVoiceState().getChannel() != channel) {
                event.reply("u need to bee in the same voice channel as me!").queue();
            }
            else {
                boolean newState = !player.isPaused();

                player.setPaused(newState);

                event.reply("ok.").queue();
                event.getChannel().sendMessage("https://tenor.com/view/smile-happy-umm-frog-peepo-gif-26307842").queue();
            }
        }
        else {
            event.reply("im not even playing music, leb me alone").queue();
        }
    }

    public void repeatCommand(@NotNull SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;

        // Check if user is inside a voice channel
        if (event.getMember().getVoiceState().getChannel() == null) {
            event.reply("u need to bee in a voice channel! dumdum???").queue();
            event.getChannel().sendMessage("https://tenor.com/view/nopers-peepo-gif-20205003").queue();
            return;
        }
        // Get user's voice channel
        VoiceChannel channel = (VoiceChannel) event.getMember().getVoiceState().getChannel();

        // Check if bot is already in a voice channel and if it's the same as the user
        if (event.getGuild().getSelfMember().getVoiceState().getChannel() != null) {
            if(event.getGuild().getSelfMember().getVoiceState().getChannel() != channel) {
                event.reply("u need to bee in the same voice channel as me!").queue();
            }
            else {
                boolean newState = !trackScheduler.isRepeat();

                trackScheduler.setRepeat(newState);

                event.reply(newState ?
                        "ok, ill repeat songs... loser"
                        : "im goin to be workin late are i?"
                ).setEphemeral(true).queue();
            }
        }
        else {
            event.reply("im not even playing music, leb me alone").queue();
        }
    }

    private void PlayTrack(SlashCommandInteractionEvent event, String trackURL) {
        AudioManager guildMusicManager = getGuildMusicManager(event.getGuild());
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                trackScheduler.queue(track);
                event.reply("musiq was addwed").setEphemeral(true).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                for (AudioTrack track : audioPlaylist.getTracks()) {
                    trackScheduler.queue(track);
                }
                event.reply("playlist was addwed").setEphemeral(true).queue();
            }

            @Override
            public void noMatches() {
                event.reply("i couldn't find a music like dat").setEphemeral(true).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                event.reply("my superiors told me i cant du dat cuz: \"" + e.getMessage() + "\"").setEphemeral(true).queue();
            }
        });
    }

    public AudioManager getGuildMusicManager(Guild guild) {
        AudioManager audioManager = guild.getAudioManager();
        if (!audioManager.isConnected() || audioManager.getSendingHandler() == null) {
            audioManager.setSendingHandler(new AudioPlayerSendHandler(player, guild));
        }

        return guild.getAudioManager();
    }
}
