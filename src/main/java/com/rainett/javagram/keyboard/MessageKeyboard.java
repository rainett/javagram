package com.rainett.javagram.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageKeyboard {

    private final List<MessageKeyboardRow> rows;

    public MessageKeyboard(List<MessageKeyboardRow> rows) {
        this.rows = rows;
    }

    public MessageKeyboard(MessageKeyboardRow... messageKeyboardRows) {
        rows = new ArrayList<>();
        rows.addAll(Arrays.asList(messageKeyboardRows));
    }

    public void addRow(MessageKeyboardRow row) {
        rows.add(row);
    }

    public InlineKeyboardMarkup toInlineKeyboardMarkup() {
        if (rows.isEmpty()) {
            return null;
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = getKeyboardFromRows();
        markup.setKeyboard(keyboard);
        return markup;
    }

    private List<List<InlineKeyboardButton>> getKeyboardFromRows() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        rows.forEach(r -> keyboard.add(r.toInlineKeyboardButtonsList()));
        return keyboard;
    }

}
