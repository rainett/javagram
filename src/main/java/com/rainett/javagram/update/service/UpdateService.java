package com.rainett.javagram.update.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateService {
    void handleUpdate(Update update);
}
