package com.rainett.javagram.keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Getter
@Setter
@AllArgsConstructor
public class MessageKeyboardButton {

    private ButtonCallback callback;
    private String text;

    public InlineKeyboardButton toInlineKeyboardButton() {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callback.toCallbackData());
        return inlineKeyboardButton;
    }

}
