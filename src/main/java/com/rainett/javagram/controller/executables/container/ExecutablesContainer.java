package com.rainett.javagram.controller.executables.container;

import com.rainett.javagram.controller.executables.container.key.UpdateKey;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

/**
 * Container for all executables (commands and callbacks) in your application
 */
public interface ExecutablesContainer {

    /**
     * Provides executable from Telegram update
     * @param update Telegram update
     * @return Executable
     */
    Optional<Object> getExecutable(Update update);

    /**
     * Returns all created executables
     * @return Map of executables
     */
    Map<UpdateKey, Object> getExecutables();

}
