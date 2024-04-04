package com.rainett.javagram.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Main controller. Receives Telegram updates as HTTP POST requests
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class UpdateController {

  private final UpdateService updateService;

  @PostMapping
  public void onUpdateReceived(@RequestBody Update update) {
    log.info("Received an update, {}", update.toString().replaceAll("\\w+=null,? ?", ""));
    updateService.handleUpdate(update);
  }

}

