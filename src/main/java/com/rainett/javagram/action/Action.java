package com.rainett.javagram.action;

import org.telegram.telegrambots.meta.api.objects.Update;

@FunctionalInterface
public interface Action {
    void run(Update update);
}
