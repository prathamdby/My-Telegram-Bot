package org.github.prathamdubey2005.interfaces;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

// Interface for the custom bot commands
public interface BotCommand {
    // Getter for the name of the command
    String getName();

    // Getter for the description of the command
    String getDescription();

    // List of the methods to execute like sendMessage, deleteMessage, etc.
    List<BotApiMethod<?>> execute(Update update, String[] args);
}
