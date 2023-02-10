package com.rainett.javagram.keyboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ButtonCallback {

    private final String callbackName;
    private final String messageSenderId;
    private String[] parameters;

    public String toCallbackData() {
        StringBuilder builder = new StringBuilder();
        builder.append(callbackName);
        builder.append("?").append(messageSenderId);
        if (parameters != null)
            Arrays.stream(parameters).forEach(p -> builder.append("?").append(p));
        return builder.toString();
    }

    public ButtonCallback(CallbackQuery callbackQuery) {
        String[] callbackDataSplit = callbackQuery.getData().split("\\?");
        String callbackName = callbackDataSplit[0];
        String messageSenderId = callbackDataSplit[1];
        String[] parameters = parseParams(callbackDataSplit);
        this.callbackName = callbackName;
        this.messageSenderId = messageSenderId;
        this.parameters = parameters;
    }

    private static String[] parseParams(String[] callbackDataSplit) {
        if (callbackDataSplit.length > 2) {
            return Arrays.copyOfRange(callbackDataSplit, 2, callbackDataSplit.length);
        }
        return null;
    }
}
