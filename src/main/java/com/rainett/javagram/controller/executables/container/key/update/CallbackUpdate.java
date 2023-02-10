package com.rainett.javagram.controller.executables.container.key.update;

import com.rainett.javagram.annotations.Callback;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Getter
@Setter
public class CallbackUpdate {

    private String callbackName;
    private boolean fromSender;

    public CallbackUpdate(Callback callback) {
        callbackName = callback.value();
        fromSender = callback.fromSender();
    }

    public CallbackUpdate(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String[] callbackDataSplit = callbackQuery.getData().split("\\?");
        callbackName = callbackDataSplit[0];
        fromSender = callbackQuery.getFrom().getId().toString().equals(callbackDataSplit[1]);
    }

    public static boolean updateMatches(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallbackUpdate that = (CallbackUpdate) o;
        return (!that.fromSender || fromSender) && Objects.equals(callbackName, that.callbackName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callbackName);
    }
}
