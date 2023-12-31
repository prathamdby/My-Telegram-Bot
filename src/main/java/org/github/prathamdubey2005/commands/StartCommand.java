package org.github.prathamdubey2005.commands;

import org.github.prathamdubey2005.Bot;
import org.github.prathamdubey2005.interfaces.BotCommand;
import org.github.prathamdubey2005.utils.CommandUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

public class StartCommand implements BotCommand {
    public StartCommand(Bot bot) {
        // Append the 'start' command to the commands HashMap
        bot.addCommand("start", this);
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Displays a welcome message.";
    }

    @Override
    public List<BotApiMethod<?>> execute(Update update, String[] args) {
        // Fetch the username of the message author using the getter
        String username = CommandUtils.getUsername(update);

        // Prepare a generic greeting
        String text = "Hello there @" + username + "! I'm a generic Telegram bot written in Java.";

        // Return the reply message getter
        return Collections.singletonList(CommandUtils.getReplyMessage(update, text));
    }
}
