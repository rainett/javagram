package com.rainett.javagram.controller.executables.container.key;

import com.rainett.javagram.annotations.Callback;
import com.rainett.javagram.annotations.Command;
import com.rainett.javagram.controller.executables.container.key.update.CallbackUpdate;
import com.rainett.javagram.controller.executables.container.key.update.CommandUpdate;
import com.rainett.javagram.exceptions.UnknownUpdateException;
import org.telegram.telegrambots.meta.api.objects.Update;

public class KeyBuilder {

    public static UpdateKey ofCommand(Command command) {
        return UpdateKey.builder()
                .commandUpdate(new CommandUpdate(command))
                .build();
    }

    public static UpdateKey ofCallback(Callback callback) {
        return UpdateKey.builder()
                .callbackUpdate(new CallbackUpdate(callback))
                .build();
    }

    public static UpdateKey ofUpdate(Update update) throws UnknownUpdateException {
        if (CommandUpdate.updateMatches(update)) {
            return UpdateKey.builder()
                    .commandUpdate(new CommandUpdate(update))
                    .build();
        }
        if (CallbackUpdate.updateMatches(update)) {
            return UpdateKey.builder()
                    .callbackUpdate(new CallbackUpdate(update))
                    .build();
        }
        return null;
    }
}
