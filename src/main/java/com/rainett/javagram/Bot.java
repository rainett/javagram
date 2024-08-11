package com.rainett.javagram;

import com.rainett.javagram.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Component
public class Bot extends SpringWebhookBot {
  private final BotConfig botConfig;

  public Bot(SetWebhook setWebhook, BotConfig botConfig) {
    super(setWebhook, botConfig.getToken());
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
}
