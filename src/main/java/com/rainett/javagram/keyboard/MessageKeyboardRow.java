package com.rainett.javagram.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents row of buttons in message keyboard
 */
public class MessageKeyboardRow {

    private final List<MessageKeyboardButton> buttons;

    public MessageKeyboardRow(List<MessageKeyboardButton> buttons) {
        this.buttons = buttons;
    }

    public MessageKeyboardRow(MessageKeyboardButton... MessageKeyboardButtons) {
        buttons = new ArrayList<>();
        buttons.addAll(Arrays.asList(MessageKeyboardButtons));
    }

    public List<InlineKeyboardButton> toInlineKeyboardButtonsList() {
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        buttons.forEach(b -> inlineKeyboardButtons.add(b.toInlineKeyboardButton()));
        return inlineKeyboardButtons;
    }
}
