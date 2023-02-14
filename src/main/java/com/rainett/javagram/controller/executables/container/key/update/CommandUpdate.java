package com.rainett.javagram.controller.executables.container.key.update;

import com.rainett.javagram.annotations.Command;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Represents update with command in it
 */
@Data
public class CommandUpdate {

    private String commandName;

    public CommandUpdate(Command command) {
        commandName = command.value();
    }

    /**
     * Command form received Telegram update
     * @param update Telegram update
     */
    public CommandUpdate(Update update) {
        commandName = "/" + update.getMessage().getText().split("\\W")[1];
    }

    /**
     * Weather update has a command inside
     * @param update Telegram update
     */
    public static boolean updateMatches(Update update) {
        return update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().startsWith("/");
    }
}
