package com.rainett.javagram.update;

import com.rainett.javagram.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
public class ApplicationReadyHandler implements ApplicationListener<ApplicationReadyEvent> {

  private final BotConfig botConfig;

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    updateWebhookUri();
  }

  private void updateWebhookUri() {
    RestTemplate template = new RestTemplate();
    String webhookSetUrl = "https://api.telegram.org/bot%s/setWebhook?url=%s";
    String formattedUrl = String.format(webhookSetUrl, botConfig.getToken(), botConfig.getPath());
    ResponseEntity<WebhookResponseDto> forEntity =
        template.getForEntity(formattedUrl, WebhookResponseDto.class);
    log.info("Webhook response body: {}", forEntity.getBody());
  }

  private record WebhookResponseDto(boolean isOk, boolean result, String description) { }
}
