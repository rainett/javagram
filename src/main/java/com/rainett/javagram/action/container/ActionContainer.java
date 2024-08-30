package com.rainett.javagram.action.container;

import com.rainett.javagram.action.Action;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ActionContainer {
    Action findByUpdate(Update update);
}
