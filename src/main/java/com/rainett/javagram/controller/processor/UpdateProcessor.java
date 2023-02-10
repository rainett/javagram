package com.rainett.javagram.controller.processor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface UpdateProcessor {

    BotApiMethod<?> onWebhookUpdateReceived(Update update);

}
