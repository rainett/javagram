package com.rainett.javagram.controller.webhook.startup;

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
public class WebhookUpdater implements ApplicationListener<ApplicationReadyEvent> {

    private final BotConfig botConfig;
    private final String WEBHOOK_SET_URL = "https://api.telegram.org/bot%s/setWebhook?url=%s";

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        updateWebhookURI();
    }

    private void updateWebhookURI() {
        RestTemplate template = new RestTemplate();
        String formattedURL = String.format(WEBHOOK_SET_URL, botConfig.getToken(), botConfig.getPath());
        ResponseEntity<WebhookResponse> forEntity = template.getForEntity(formattedURL, WebhookResponse.class);
        log.info("Webhook response body: {}", forEntity.getBody());
    }
}
