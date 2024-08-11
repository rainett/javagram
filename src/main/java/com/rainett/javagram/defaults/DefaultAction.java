package com.rainett.javagram.defaults;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class DefaultAction {
  public void emptyMethod(Update update) {
    log.info("No suitable BotAction was found for update with id = [%d]"
        .formatted(update.getUpdateId()));
  }
}
