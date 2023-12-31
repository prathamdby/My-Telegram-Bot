package org.github.prathamdubey2005.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandUtils {
    // Get method to fetch the username of the message author from the provided update
    public static String getUsername(Update update) {
        // Fetch the direct username (@)
        String username = update.getMessage().getFrom().getUserName();

        // If the direct username is empty, join the first and last name
        if (username == null || username.isEmpty()) {
            username = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
        }

        return username;
    }

    // Get method to fetch the group name
    public static String getGroupName(Update update) {
        return update.getMessage().getChat().getTitle();
    }

    // Get method to fetch a ready-made reply message with the provided text
    public static SendMessage getReplyMessage(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText(text);

        return message;
    }

    // Get method to fetch a ready-made reply to the replied message with the provided text
    public static SendMessage getReplyToReplyMessage(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getReplyToMessage().getMessageId());
        message.setText(text);

        return message;
    }

    // Get method to fetch a ready-made send message with the provided text
    public static SendMessage getSendMessage(Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setText(text);

        return message;
    }

    // Get method to fetch a ready-made delete message
    public static DeleteMessage getDeleteMessage(Update update) {
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(update.getMessage().getChatId().toString());
        delete.setMessageId(update.getMessage().getMessageId());

        return delete;
    }
}
