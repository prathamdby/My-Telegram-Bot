package org.github.prathamdubey2005.commands;

import org.github.prathamdubey2005.Bot;
import org.github.prathamdubey2005.interfaces.BotCommand;
import org.github.prathamdubey2005.utils.CommandUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HelpCommand implements BotCommand {
    // Initialize the HashMap to fetch all the commands for later use
    Map<String, BotCommand> commands;

    public HelpCommand(Bot bot) {
        // Fetch the commands HashMap using the getter
        commands = bot.getCommands();

        // Append the 'help' command to the commands HashMap
        bot.addCommand("help", this);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Provides a list of available commands.";
    }

    @Override
    public List<BotApiMethod<?>> execute(Update update, String[] args) {
        // Initialize a StringBuilder to prepare a list of all the commands in 'command -> description' format
        StringBuilder text = new StringBuilder();

        // Iterate over the previously fetched commands HashMap
        commands.forEach((name, command) -> {
            // Append to the StringBuilder in the 'command -> description' format
            String commandLine = "/" + command.getName() + " -> " + command.getDescription();
            text.append(commandLine);
            text.append("\n");
        });

        // Return the reply message getter
        return Collections.singletonList(CommandUtils.getReplyMessage(update, text.toString()));
    }
}
