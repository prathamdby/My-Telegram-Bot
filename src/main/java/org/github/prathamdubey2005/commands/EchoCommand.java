package org.github.prathamdubey2005.commands;

import org.github.prathamdubey2005.Bot;
import org.github.prathamdubey2005.interfaces.BotCommand;
import org.github.prathamdubey2005.utils.CommandUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EchoCommand implements BotCommand {
    public EchoCommand(Bot bot) {
        // Append the 'echo' command to the commands HashMap
        bot.addCommand("echo", this);
    }

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return "Repeats the provided text.";
    }

    @Override
    public List<BotApiMethod<?>> execute(Update update, String[] args) {
        // Prepare a list of methods to run
        List<BotApiMethod<?>> toReturn = new ArrayList<>();

        // Fetch the text to echo by joining the arguments
        String text = String.join(" ", args);

        // Handle empty text by returning a request message
        if (text.isEmpty()) {
            text = "Please provide some text to echo.";
            return Collections.singletonList(CommandUtils.getReplyMessage(update, text));
        }

        // Delete the user command to prepare for the 'echo' effect
        toReturn.add(CommandUtils.getDeleteMessage(update));

        // Check if the user message was a reply to another message
        Message replyMessage = update.getMessage().getReplyToMessage();
        if (replyMessage != null) {
            // If it was, echo will reply to that message
            toReturn.add(CommandUtils.getReplyToReplyMessage(update, text));
        } else {
            // Otherwise, just echo in the group
            toReturn.add(CommandUtils.getSendMessage(update, text));
        }

        // Return the final list of methods to execute
        return toReturn;
    }
}
