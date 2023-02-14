package com.rainett.javagram.controller.processor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Processes all Telegram updates
 */
@Component
public interface UpdateProcessor {

    /**
     * Processing method
     * @param update Telegram update
     * @return Result method. Typically, is ignored
     */
    BotApiMethod<?> onWebhookUpdateReceived(Update update);

}
