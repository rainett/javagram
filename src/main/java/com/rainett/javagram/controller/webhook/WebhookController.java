package com.rainett.javagram.controller.webhook;

import com.rainett.javagram.controller.processor.UpdateProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Main controller. Receives Telegram updates as HTTP POST requests
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class WebhookController {

    private final UpdateProcessor processor;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        log.info("Received an update, {}", update.toString().replaceAll("\\w+=null,? ?", ""));
        return processor.onWebhookUpdateReceived(update);
    }

}

