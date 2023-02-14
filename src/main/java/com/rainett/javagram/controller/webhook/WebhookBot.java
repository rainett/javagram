package com.rainett.javagram.controller.webhook;

import com.rainett.javagram.config.BotConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

/**
 * Main bot class. Executes all Telegram methods, setups webhook.
 */
@Getter
@Setter
@Slf4j
public class WebhookBot extends SpringWebhookBot {

    private BotConfig botConfig;

    public WebhookBot(SetWebhook setWebhook, BotConfig botConfig) {
        super(setWebhook);
        this.botConfig = botConfig;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return botConfig.getPath();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

}
