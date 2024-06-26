[jda-repo]: https://github.com/DV8FromTheWorld/JDA

# Poggies - Your wholesome froggy friend!

Poggies is a Discord bot designed to bring fun and utility to your server. It features various commands for interacting with users, playing music, and more.
Poggies has also hidden interactions that only activate by talking to him in chat, try to find them!

## Commands
- `/say <message> [channel]`: Make Poggies say something in the specified channel or the current channel if none is provided.
- `/action <action> [member]`: Make Poggies perform a fun action, such as hugging or dancing.
- `/annoy <member>`: Make Poggies annoy someone.
- `/play <music>`: Instruct Poggies to play or queue a song.
- `/stop`: Stop the music playback.
- `/pause`: Pause the currently playing music.
- `/repeat`: Toggle repeat mode for the music.

## How to Run
1. Clone this repository to your local machine.
2. Ensure you have Java and Maven installed.
3. Create a `.env` file in the project root directory and add your Discord bot token
4. Open IDE and run the `PoggiesBot` file
5. Invite Poggies to Your Server: Create a Discord bot application and invite Poggies to your Discord server using the OAuth2 URL generated by Discord.

##Environment Variables
- TOKEN: Your Discord bot token. Get it by creating a new bot application on the Discord Developer Portal.
  
## Libraries
- **[JDA][jda-repo]** - Discord API Wrapper written in Java.
- **[JDA-Utilities](https://github.com/JDA-Applications/JDA-Utilities)** - Extension for JDA to assist in Discord bot creation.
- **[Lavaplayer](https://github.com/sedmelluq/lavaplayer)** - Audio player library for Discord.
