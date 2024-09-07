package com.rainett.javagram.update.appready;

import com.rainett.javagram.annotations.command.Command;
import com.rainett.javagram.bot.WebhookBot;
import com.rainett.javagram.config.BotConfig;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Slf4j
@Component
public class ApplicationReadyHandler implements ApplicationListener<ApplicationReadyEvent> {
    private static final String WEBHOOK_URL = "https://api.telegram.org/bot%s/setWebhook?url=%s";
    private final BotConfig botConfig;
    private final CommandRegisterer commandRegisterer;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        updateWebhookUrl(event);
        registerCommands(event);
    }

    private void registerCommands(ApplicationReadyEvent event) {
        List<Command> commands = event.getApplicationContext()
                .getBeansWithAnnotation(Command.class)
                .values().stream()
                .map(action -> action.getClass().getDeclaredAnnotation(Command.class))
                .toList();
        commandRegisterer.registerCommands(commands);
    }

    private void updateWebhookUrl(ApplicationReadyEvent event) {
        boolean isWebhook = !event.getApplicationContext()
                .getBeansOfType(WebhookBot.class)
                .isEmpty();
        if (isWebhook) {
            RestTemplate template = new RestTemplate();
            String formattedUrl =
                    String.format(WEBHOOK_URL, botConfig.getToken(), botConfig.getPath());
            ResponseEntity<String> forEntity = template.getForEntity(formattedUrl, String.class);
            log.info("Webhook response body: {}", forEntity.getBody());
        }
    }
}
