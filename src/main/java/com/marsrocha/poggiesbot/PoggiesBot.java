package com.marsrocha.poggiesbot;

import com.marsrocha.poggiesbot.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class PoggiesBot {

    private final Dotenv config;
    private final JDA jda;

    public PoggiesBot() throws LoginException {
        config = Dotenv.configure().load();

        jda = JDABuilder.createDefault(config.get("TOKEN"))
                .setActivity(Activity.playing("with your mom"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT) // also enable privileged intent
                .addEventListeners(new EventListener()) // Register listeners
                .build();
    }

    public static void main(String[] args) {

        try {
            new PoggiesBot();
        }
        catch (Exception e) {
            System.out.println("Provided TOKEN is invalid");
        }


        System.out.println("PoggiesBot is online!");
    }
}