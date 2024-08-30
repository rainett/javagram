package com.rainett.javagram.bot;

import com.rainett.javagram.config.BotConfig;
import com.rainett.javagram.update.service.UpdateService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class LongPollingBot extends TelegramLongPollingBot {
    private static final String NULL_VALUES_REGEX = "\\w+=null,? ?";
    private static final String REPLACEMENT = "";
    private final BotConfig botConfig;
    private final ObjectProvider<UpdateService> serviceProvider;

    public LongPollingBot(BotConfig botConfig, ObjectProvider<UpdateService> service) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.serviceProvider = service;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String nullFreeUpdate = update.toString().replaceAll(NULL_VALUES_REGEX, REPLACEMENT);
        log.info("Received an update, {}", nullFreeUpdate);
        UpdateService updateService = serviceProvider.getIfAvailable();
        if (updateService == null) {
            throw new RuntimeException("UpdateService is not available");
        }
        updateService.handleUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @PostConstruct
    private void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to register bot", e);
        }
    }
}
