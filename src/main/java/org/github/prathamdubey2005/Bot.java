package org.github.prathamdubey2005;

import org.github.prathamdubey2005.commands.EchoCommand;
import org.github.prathamdubey2005.commands.HelpCommand;
import org.github.prathamdubey2005.commands.StartCommand;
import org.github.prathamdubey2005.interfaces.BotCommand;
import org.github.prathamdubey2005.utils.BotUtils;
import org.github.prathamdubey2005.utils.CommandUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bot extends TelegramLongPollingBot {
    // HashMap to store commands in 'name': 'command' format
    private final Map<String, BotCommand> commands;
    // File name for the properties file
    private final String propertyFile = "config.properties";
    // Bot necessities
    private String botUsername;
    private String botToken;

    // Initialize the bot
    public Bot() {
        // Fetch and update the username and token variables
        botUsername = BotUtils.getProperty(propertyFile, "bot.username");
        botToken = BotUtils.getProperty(propertyFile, "bot.token");

        // Initialize the HasMap and call the individual command classes
        commands = new HashMap<>();
        new StartCommand(this);
        new EchoCommand(this);
        new HelpCommand(this);
    }

    // Add method to append commands to the HashMap
    public void addCommand(String name, BotCommand command) {
        this.commands.put(name, command);
    }

    // Get method to fetch the HashMap containing all the commands
    public Map<String, BotCommand> getCommands() {
        return this.commands;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Guards for non-existent messages
        if (!update.hasMessage()) return;
        if (!update.getMessage().hasText()) return;

        // Split up the text into raw arguments and command
        String[] rawArgs = update.getMessage().getText().split(" ");
        String rawCommand = rawArgs[0];

        // Ignore all the non-command messages
        if (!rawCommand.startsWith("/")) return;

        // Ignore all the other bot commands
        if (rawCommand.contains("@"))
            if (!rawCommand.endsWith(botUsername)) return;

        // Fetch the final command and args array
        String[] args = Arrays.copyOfRange(rawArgs, 1, rawArgs.length);
        String command = rawCommand.replace("/", "");

        if (command.contains("@"))
            command = command.replace("@" + botUsername, "");

        // Find and execute the command
        BotCommand botCommand = commands.get(command);

        if (botCommand != null) {
            // Fetch the list of methods and execute each of them
            List<BotApiMethod<?>> methodsToExecute = botCommand.execute(update, args);
            for (BotApiMethod<?> method : methodsToExecute) {
                try {
                    execute(method);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Log if it is an unknown command
            String text = "[DEBUG] [" + update.getMessage().getChat().getTitle() + "] [" + CommandUtils.getUsername(update) + "] Unknown command '" + command + "' was called. Ignoring it.";
            System.out.println(text);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
