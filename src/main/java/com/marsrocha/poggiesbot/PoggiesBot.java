package com.marsrocha.poggiesbot;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class PoggiesBot {

    private final Dotenv config;

    public PoggiesBot() throws LoginException {
        config = Dotenv.configure().load();

        JDA jda = JDABuilder.createDefault(config.get("TOKEN"))
                .setActivity(Activity.playing("with your mom"))
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