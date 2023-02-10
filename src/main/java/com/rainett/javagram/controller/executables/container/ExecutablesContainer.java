package com.rainett.javagram.controller.executables.container;

import com.rainett.javagram.controller.executables.container.key.UpdateKey;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

public interface ExecutablesContainer {

    Optional<Object> getExecutable(Update update);

    Map<UpdateKey, Object> getExecutables();

}
